package com.chs.youranimelist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.viewmodel.MainViewModel

class SecondFragment : Fragment() {
    private lateinit var binding:FragmentAnimeDetailBinding
    private val args: SecondFragmentArgs by navArgs()
    private val repository by lazy { AnimeListRepository() }
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeDetailBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAnimeInfo()
    }

    private fun initAnimeInfo() {
        viewModel.getAnimeInfo(args.anime.id!!).observe(viewLifecycleOwner,{
            binding.model = it.data.page.media[0]
        })
    }
}