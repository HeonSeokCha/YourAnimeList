package com.chs.youranimelist.ui.browse.anime

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.R
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.Constant
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.google.android.material.tabs.TabLayoutMediator

class AnimeDetailFragment : Fragment() {
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var trailerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeDetailViewModel(repository, requireActivity().application)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
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
        bindProgressButton(binding.mediaSaveList)
    }

    private fun checkAnimeList() {
        viewModel.checkAnimeList(arguments?.getInt(Constant.TARGET_ID)!!)
            .observe(viewLifecycleOwner, {
                if (it.size == 1 && it[0].animeId == arguments?.getInt(Constant.TARGET_ID)!!) {
                    viewModel.initAnimeList = it[0]
                    binding.mediaSaveList.apply {
                        val animatedDrawable =
                            ContextCompat.getDrawable(this.context!!, R.drawable.ic_check)!!
                        animatedDrawable.setBounds(0, 0, 50, 50)
                        showDrawable(animatedDrawable) {
                            buttonText = "Saved"
                        }
                    }
                } else {
                    binding.mediaSaveList.apply {
                        hideDrawable()
                        text = "ADD MY LIST"
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