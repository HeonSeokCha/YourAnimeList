package com.chs.youranimelist.ui.browse.anime.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentAnimeRecommendBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragmentDirections
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeRecommendFragment : Fragment() {
    private var _binding: FragmentAnimeRecommendBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeRecommendViewModel by viewModels()
    private var isLoading: Boolean = false
    private var animeRecommendAdapter: AnimeRecommendAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getRecommendList(arguments?.getInt(Constant.TARGET_ID)!!)
        getRecommendList()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun getRecommendList() {
        viewModel.animeRecommendResponse.observe(viewLifecycleOwner) {
            when (it.responseState) {
                ResponseState.LOADING -> binding.progressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    if (!viewModel.hasNextPage) {
                        return@observe
                    }

                    if (isLoading) {
                        viewModel.animeRecList.removeAt(viewModel.animeRecList.lastIndex)
                        isLoading = false
                    }
                    viewModel.hasNextPage =
                        it.data?.media?.recommendations?.pageInfo?.hasNextPage ?: false
                    it.data?.media?.recommendations?.edges?.forEach { recommend ->
                        viewModel.animeRecList.add(recommend)
                    }
                    animeRecommendAdapter?.notifyItemRangeChanged(
                        (viewModel.page * 10),
                        it.data?.media?.recommendations?.edges?.size!!
                    )
                    binding.progressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimeRecommend.apply {
            animeRecommendAdapter = AnimeRecommendAdapter(viewModel.animeRecList) { id, idMal ->
                findNavController().navigate(
                    AnimeDetailFragmentDirections.actionAnimeDetailSelf(
                        id,
                        idMal
                    )
                )
            }
            this.adapter = animeRecommendAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        !recyclerView.canScrollVertically(1) && !isLoading
                    ) {
                        loadMore()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.animeRecList.add(null)
            animeRecommendAdapter?.notifyItemInserted(viewModel.animeRecList.lastIndex)
            viewModel.page += 1
            viewModel.getRecommendList(arguments?.getInt(Constant.TARGET_ID)!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeRecommend.adapter = null
        _binding = null
    }
}