package com.chs.youranimelist.presentation.browse.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentStudioBinding
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.presentation.base.BaseFragment
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioFragment : BaseFragment() {
    private var _binding: FragmentStudioBinding? = null
    private val binding get() = _binding!!
    private val args: StudioFragmentArgs by navArgs()
    private var studioAnimeAdapter: StudioAnimeAdapter? = null
    private var isLoading: Boolean = false
    private val viewModel: StudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.studioId = args.studioId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStudioAnime()
        initClick()
        initRecyclerView()
        initStudio()
    }

    private fun initStudio() {
        viewModel.studioResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Success -> {
                    if (isLoading) {
                        viewModel.studioAnimeList.removeAt(viewModel.studioAnimeList.lastIndex)
                        studioAnimeAdapter?.notifyItemRemoved(viewModel.studioAnimeList.size)
                        isLoading = false
                    }
                    binding.model = it.data
                    viewModel.hasNextPage = it.data?.media?.pageInfo?.hasNextPage ?: false
                    it.data!!.media!!.edges!!.forEach { edge ->
                        viewModel.studioAnimeList.add(edge!!)
                    }
                    studioAnimeAdapter?.submitList(viewModel.studioAnimeList.toMutableList())
                }
                is NetworkState.Error -> {
                    isLoading = false
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.studioAnimeList.add(null)
            studioAnimeAdapter?.notifyItemInserted(viewModel.studioAnimeList.lastIndex)
            viewModel.page += 1
            viewModel.getStudioAnime()
        }
    }

    private fun initClick() {
        binding.studioToolbars.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.txtStudioSort.setOnClickListener {
            AlertDialog.Builder(this.requireContext())
                .setItems(Constant.animeSortArray) { _, which ->
                    viewModel.selectsort = Constant.animeSortList[which]
                    binding.txtStudioSort.text = Constant.animeSortArray[which]
                    viewModel.refresh()
                }.show()
        }
    }

    private fun initRecyclerView() {
        binding.rvStudio.apply {
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

            studioAnimeAdapter = StudioAnimeAdapter { id, idMal ->
                val action =
                    StudioFragmentDirections.actionStudioFragmentToAnimeDetailFragment(
                        id,
                        idMal
                    )
                findNavController().navigate(action)
            }
            this.adapter = studioAnimeAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        studioAnimeAdapter = null
    }
}