package com.chs.youranimelist.ui.browse.anime

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var trailerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeDetailViewModel(repository, requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAnimeDetail(arguments?.getInt(Constant.TARGET_ID).toInput())
        checkAnimeList()
        initAnimeInfo()
        initTabView(
            arguments?.getInt(Constant.TARGET_ID)!!,
            arguments?.getInt(Constant.TARGET_ID_MAL)!!
        )
        initClick()
    }

    private fun checkAnimeList() {
        val bitmap: Bitmap = BitmapFactory.decodeResource(
            requireActivity().resources,
            R.drawable.ic_default
        )
        viewModel.checkAnimeList(arguments?.getInt(Constant.TARGET_ID)!!)
            .observe(viewLifecycleOwner, {
                if (it.size == 1 && it[0].animeId == arguments?.getInt(Constant.TARGET_ID)!!) {
                    viewModel.initAnimeList = it[0]

                    binding.mediaSaveList.doneLoadingAnimation(R.color.red_500, bitmap)

                } else {
                    binding.mediaSaveList.apply {
                        text = "ADD MY LIST"
                    }
                    binding.mediaSaveList.doneLoadingAnimation(R.color.red_500, bitmap)
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
            binding.mediaSaveList.text = ""
            binding.mediaSaveList.startAnimation()
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