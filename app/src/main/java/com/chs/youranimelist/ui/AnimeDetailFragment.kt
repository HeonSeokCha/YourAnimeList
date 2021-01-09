package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect

class AnimeDetailFragment : Fragment() {
    private lateinit var binding:FragmentAnimeDetailBinding
    private val repository by lazy { AnimeRepository() }
    private val args: AnimeDetailFragmentArgs by navArgs()
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
        initAnimeInfo(args.animeId.toInput())
    }

    private fun initAnimeInfo(animeId: Input<Int>) {
        viewModel.getAnimeInfo(animeId).observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            binding.model = it
                            binding.detailPageProgressBar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(this@AnimeDetailFragment.context,
                                netWorkState.message, Toast.LENGTH_SHORT).show()
                            binding.detailPageProgressBar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Loading -> {
                            binding.detailPageProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        })
    }
}