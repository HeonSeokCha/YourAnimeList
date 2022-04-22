package com.chs.youranimelist.presentation.browse.anime

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
import androidx.navigation.fragment.navArgs
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeDetailBinding
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseFragment() {
    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailFragmentArgs by navArgs()
    private lateinit var trailerId: String
    private val viewModel: AnimeDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAnimeDetail(args.id)
        checkAnimeList()
        initAnimeInfo()
        initTabView(args.id, args.idMal)
        initClick()
    }

    private fun checkAnimeList() {
        viewModel.checkAnimeList(args.id)
            .observe(viewLifecycleOwner) { animeInfo ->
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
            }
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
        viewModel.animeDetailResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NetworkState.Success -> {
                    binding.model = it.data!!.media
                    trailerId = it.data.media?.trailer?.id.toString()
                    viewModel.animeDetail = it.data.media
                    binding.progressBar.isVisible = false
                    binding.btnTrailerPlay.isVisible = true
                }
                is NetworkState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message, Toast.LENGTH_LONG
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun initTabView(animeId: Int, idMal: Int) {
        binding.viewPagerAnimeDetail.adapter =
            AnimeDetailViewPagerAdapter(this, animeId, idMal)
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
            viewModel.insertAnimeList(viewModel.animeDetail!!)
        } else if (viewModel.initAnimeList != null) {
            viewModel.deleteAnimeList(viewModel.initAnimeList!!)
            viewModel.initAnimeList = null
        }
    }

    private fun trailerPlay(videoId: String) {
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(
                requireActivity(),
                Uri.parse("https://www.youtube.com/watch?v=$videoId")
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.animeDetail = null
        viewModel.initAnimeList = null
        _binding = null
    }
}