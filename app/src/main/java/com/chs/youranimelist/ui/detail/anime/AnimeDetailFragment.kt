package com.chs.youranimelist.ui.detail.anime

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.home.MainActivity
import com.chs.youranimelist.ui.home.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect

class AnimeDetailFragment : Fragment() {
    private var _binding:FragmentAnimeDetailBinding? = null
    private val repository by lazy { AnimeRepository() }
    private val args: AnimeDetailFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel
    private lateinit var trailerId: String
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeDetailBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).binding.toolbar.title = args.animeName
        initAnimeInfo(args.animeId.toInput())
        initClick()
    }

    private fun initClick() {
        binding.btnTrailerPlay.setOnClickListener {
            trailerPlay(trailerId)
        }
    }

    private fun initAnimeInfo(animeId: Input<Int>) {
        viewModel.getAnimeInfo(animeId).observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when (netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            binding.model = it
                            initTabView(it)
                            trailerId = it.trailer?.id.toString()
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

    private fun initTabView(animeInfo: AnimeDetailQuery.Media) {
        var adapter = AnimeDetailViewPagerAdapter(requireActivity(), animeInfo)
        binding.viewPagerAnimeDetail.adapter = adapter
        binding.viewPagerAnimeDetail.isUserInputEnabled = false
        TabLayoutMediator(binding.tabAnimeDetail, binding.viewPagerAnimeDetail) { tab, position ->
            var tabArr: List<String> = listOf("Overview", "Characters", "Recommend")
            for (i in 0..position) {
                tab.text = tabArr[i]
            }
        }.attach()
    }

    private fun trailerPlay(videoId: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireActivity(), Uri.parse("https://www.youtube.com/watch?v=$videoId"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}