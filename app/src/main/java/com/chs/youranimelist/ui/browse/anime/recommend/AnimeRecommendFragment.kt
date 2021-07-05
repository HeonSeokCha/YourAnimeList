package com.chs.youranimelist.ui.browse.anime.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.databinding.FragmentAnimeRecommendBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragmentDirections


class AnimeRecommendFragment : Fragment() {
    private var _binding: FragmentAnimeRecommendBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeRecommendViewModel
    private var animeRecommendAdapter: AnimeRecommendAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeRecommendViewModel(repository)
    }

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
        viewModel.getRecommendList(arguments?.getInt("id")!!)
        getRecommendList()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun getRecommendList() {
        viewModel.animeRecommendResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.progressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    it.data?.media?.recommendations?.edges?.forEach { recommend ->
                        viewModel.animeRecList.add(recommend)
                    }
                    animeRecommendAdapter?.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeRecommend.apply {
            animeRecommendAdapter = AnimeRecommendAdapter(viewModel.animeRecList) { id, idMal ->
                val action =
                    AnimeDetailFragmentDirections.actionAnimeDetailFragmentSelf(id, idMal)
                findNavController().navigate(action)
            }
            this.adapter = animeRecommendAdapter
            this.layoutManager = LinearLayoutManager(this@AnimeRecommendFragment.context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeRecommend.adapter = null
        _binding = null
    }
}