package com.chs.youranimelist.ui.browse.anime

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.R
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.util.Constant
import com.google.android.material.tabs.TabLayoutMediator

class AnimeDetailFragment : BaseFragment() {
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailFragmentArgs by navArgs()
    private val viewModel: AnimeDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AnimeDetailViewModel(requireActivity().application) as T
            }
        }
    }

    private lateinit var trailerId: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAnimeDetail(args.id.toInput())
        checkAnimeList()
        initAnimeInfo()
        initTabView(
            args.id,
            args.idMal
        )
        initClick()
    }

    private fun checkAnimeList() {
        args.id
        viewModel.checkAnimeList(args.id)
            .observe(viewLifecycleOwner, { animeInfo ->
                if (animeInfo != null && animeInfo.animeId == args.id) {
                    viewModel.initAnimeList = animeInfo!!
                    binding.mediaSaveList.apply {
                        this.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
                        this.text = "SAVED"
                    }
                } else {
                    binding.mediaSaveList.apply {
                        this.icon = null
                        this.text = "ADD MY LIST"
                    }
                }
            })
    }

    private fun initClick() {
        binding.animeToolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.btnTrailerPlay.setOnClickListener {
            trailerPlay(trailerId)
        }

        binding.mediaSaveList.setOnClickListener {
            saveList()
        }
    }

    private fun initAnimeInfo() {
        viewModel.animeDetailResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                ResponseState.SUCCESS -> {
                    binding.model = it.data!!.media
                    trailerId = it.data.media?.trailer?.id.toString()
                    viewModel.animeDetail = it.data.media
                    binding.progressBar.isVisible = false
                    binding.btnTrailerPlay.isVisible = true
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

    private fun initTabView(animeId: Int, idMal: Int) {
        binding.viewPagerAnimeDetail.adapter =
            AnimeDetailViewPagerAdapter(requireActivity(), animeId, idMal)
        binding.viewPagerAnimeDetail.isUserInputEnabled = false

        TabLayoutMediator(binding.tabAnimeDetail, binding.viewPagerAnimeDetail) { tab, position ->
            var tabArr: List<String> = listOf("Overview", "Characters", "Recommend")
            for (i in 0..position) {
                tab.text = tabArr[i]
            }
        }.attach()
    }

    private fun saveList() {
        if (viewModel.animeDetail != null && viewModel.initAnimeList == null) {
            with(viewModel.animeDetail!!) {
                viewModel.insertAnimeList(
                    Anime(
                        animeId = this.id,
                        idMal = this.idMal!!,
                        title = this.title!!.english ?: this.title.romaji!!,
                        format = this.format.toString(),
                        status = this.status.toString(),
                        season = this.season.toString(),
                        seasonYear = this.seasonYear ?: 0,
                        episode = this.episodes ?: 0,
                        coverImage = this.coverImage?.extraLarge,
                        bannerImage = this.bannerImage,
                        averageScore = this.averageScore ?: 0,
                        favorites = this.favourites,
                        studio = this.studios!!.edges?.get(0)!!.node!!.name,
                        genre = this.genres ?: listOf(),
                    )
                )
            }
        } else if (viewModel.initAnimeList != null) {
            viewModel.deleteAnimeList(viewModel.initAnimeList!!)
            viewModel.initAnimeList = null
        }
    }

    private fun trailerPlay(videoId: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(requireActivity(), Uri.parse("https://www.youtube.com/watch?v=$videoId"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.animeDetail = null
        viewModel.initAnimeList = null
        _binding = null
    }
}