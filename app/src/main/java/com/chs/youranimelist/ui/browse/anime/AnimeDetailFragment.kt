package com.chs.youranimelist.ui.browse.anime

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.google.android.material.tabs.TabLayoutMediator

class AnimeDetailFragment : Fragment() {
    private var _binding: FragmentAnimeDetailBinding? = null
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var trailerId: String
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        viewModel = AnimeDetailViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAnimeInfo(arguments?.getInt("id").toInput())
        initAnimeInfo()
        initTabView(arguments?.getInt("id")!!)
        initClick()
    }

    private fun initClick() {
        binding.animeToolbar.setNavigationOnClickListener { activity?.finish() }
        binding.btnTrailerPlay.setOnClickListener {
            trailerPlay(trailerId)
        }
    }

    private fun initAnimeInfo() {
        viewModel.animeDetailResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.progressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    binding.model = it.data!!.media
                    trailerId = it.data?.media?.trailer?.id.toString()
                    binding.progressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@AnimeDetailFragment.context,
                        it.message, Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }
        })
    }

    private fun initTabView(animeId: Int) {
        binding.viewPagerAnimeDetail.adapter =
            AnimeDetailViewPagerAdapter(requireActivity(), animeId)
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