package com.chs.youranimelist.ui.detail.anime.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.databinding.FragmentAnimeOverviewBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.home.MainViewModel


class AnimeOverviewFragment(private val animeInfo:AnimeDetailQuery.Media) : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeOverviewBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.model = animeInfo
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