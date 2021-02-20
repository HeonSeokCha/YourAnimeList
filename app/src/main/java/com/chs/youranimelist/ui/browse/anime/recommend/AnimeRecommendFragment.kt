package com.chs.youranimelist.ui.browse.anime.recommend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.databinding.FragmentAnimeRecommendBinding
import com.chs.youranimelist.network.repository.AnimeRepository


class AnimeRecommendFragment : Fragment() {
    private var _binding: FragmentAnimeRecommendBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeRecommendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeRecommendBinding.inflate(inflater, container, false)
        viewModel = AnimeRecommendViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getRecommendList() {
        viewModel
    }

    private fun initRecyclerView() {

    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}