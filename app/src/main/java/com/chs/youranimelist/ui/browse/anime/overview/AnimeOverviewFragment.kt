package com.chs.youranimelist.ui.browse.anime.overview

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeOverviewBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragmentDirections
import com.chs.youranimelist.util.Constant


class AnimeOverviewFragment : Fragment() {
    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    private var seasonYear: Int = 0
    private val viewModel by viewModels<AnimeOverviewViewModel>()
    private lateinit var relationAdapter: AnimeOverviewRelationAdapter
    private lateinit var genreAdapter: AnimeOverviewGenreAdapter
    private lateinit var linkAdapter: AnimeOverviewLinkAdapter
    private lateinit var studioAdapter: AnimeOverviewStudioAdapter
    private lateinit var producerAdapter: AnimeOverviewStudioAdapter
    private lateinit var season: MediaSeason

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.getInt(Constant.TARGET_ID_MAL)!! != 0) {
            viewModel.getAnimeTheme(arguments?.getInt(Constant.TARGET_ID_MAL)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAnimeOverview(arguments?.getInt(Constant.TARGET_ID)!!)
        initRecyclerView()
        getAnimeInfo()
        initClick()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun initClick() {
        binding.btnExpand.setOnClickListener {
            if (binding.txtAnimeDescriptionPreview.isVisible) {
                binding.txtAnimeDescriptionPreview.visibility = View.GONE
                binding.txtAnimeDescription.visibility = View.VISIBLE
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_up)
            } else {
                binding.txtAnimeDescriptionPreview.visibility = View.VISIBLE
                binding.txtAnimeDescription.visibility = View.GONE
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_down)
            }
        }

        binding.txtAnimeOverviewSeason.setOnClickListener {
            findNavController().navigate(
                AnimeDetailFragmentDirections.actionAnimeDetailToSorted(
                    sortType = Constant.TARGET_SEASON,
                    season = season,
                    year = seasonYear
                )
            )
        }
    }

    private fun getAnimeInfo() {
        viewModel.animeOverviewResponse.observe(viewLifecycleOwner) {
            when (it.responseState) {
                ResponseState.SUCCESS -> {
                    binding.model = it.data?.media!!
                    if (it.data?.media.season != null && it.data.media.seasonYear != null) {
                        season = it.data?.media.season
                        seasonYear = it.data.media.seasonYear
                    }

                    it.data.media.relations?.relationsEdges?.forEach { relation ->
                        viewModel.animeOverviewRelationList.add(relation)
                    }

                    if (viewModel.animeOverviewRelationList.isEmpty()) {
                        binding.inOverviewLayoutRelation.isVisible = false
                    }

                    it.data.media.genres?.forEach { genres ->
                        viewModel.animeGenresList.add(genres!!)
                    }

                    it.data.media.externalLinks?.forEach { links ->
                        viewModel.animeLinkList.add(links)
                    }

                    if (viewModel.animeLinkList.isEmpty()) {
                        binding.inOverviewLayoutLinks.isVisible = false
                    }


                    it.data.media.studios?.studiosEdges?.forEach { studiosEdge ->
                        if (studiosEdge?.isMain == true) {
                            viewModel.animeStudioList.add(studiosEdge.studiosNode!!)
                        } else {
                            viewModel.animeProducerList.add(studiosEdge?.studiosNode!!)
                        }
                    }

                    if (viewModel.animeStudioList.isNotEmpty()) {
                        binding.txtAnimeStudio.isVisible = true
                        binding.rvAnimeOverviewStudio.isVisible = true
                        if (viewModel.animeProducerList.isNotEmpty()) {
                            binding.txtAnimeProducer.isVisible = true
                            binding.rvAnimeOverviewProducer.isVisible = true
                        }
                    }

                    relationAdapter?.notifyDataSetChanged()
                    genreAdapter?.notifyDataSetChanged()
                    linkAdapter?.notifyDataSetChanged()
                    studioAdapter?.notifyDataSetChanged()
                    producerAdapter?.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.animeOverviewThemeResponse.observe(viewLifecycleOwner) {
                viewModel.animeDetails = it
                initAnimeTheme()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimeOverviewRelation.apply {
            relationAdapter =
                AnimeOverviewRelationAdapter(viewModel.animeOverviewRelationList) { id, idMal ->
                    val action =
                        AnimeDetailFragmentDirections.actionAnimeDetailSelf(
                            id,
                            idMal
                        )
                    findNavController().navigate(action)
                }
            this.adapter = relationAdapter
            this.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        binding.rvAnimeOverviewGenre.apply {
            genreAdapter = AnimeOverviewGenreAdapter(viewModel.animeGenresList) {
                val action =
                    AnimeDetailFragmentDirections.actionAnimeDetailToSortedGenre(
                        sortType = Constant.TARGET_GENRE,
                        genre = it
                    )
                findNavController().navigate(action)
            }
            this.adapter = genreAdapter
        }

        binding.rvAnimeOverviewLinks.apply {
            linkAdapter = AnimeOverviewLinkAdapter(viewModel.animeLinkList) {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(this@AnimeOverviewFragment.requireContext(), Uri.parse(it))
            }
            this.adapter = linkAdapter
        }

        binding.rvAnimeOverviewStudio.apply {
            studioAdapter = AnimeOverviewStudioAdapter(viewModel.animeStudioList) { studioId ->
                val action =
                    AnimeDetailFragmentDirections.actionAnimeDetailFragmentToStudioFragment(
                        studioId
                    )
                findNavController().navigate(action)
            }
            this.adapter = studioAdapter
            this.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
        }

        binding.rvAnimeOverviewProducer.apply {
            producerAdapter = AnimeOverviewStudioAdapter(viewModel.animeProducerList) { studioId ->
                val action =
                    AnimeDetailFragmentDirections.actionAnimeDetailFragmentToStudioFragment(
                        studioId
                    )
                findNavController().navigate(action)
            }
            this.adapter = producerAdapter
            this.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun initAnimeTheme() {
        if (viewModel.animeDetails?.openingThemes?.isNullOrEmpty() == false) {
            binding.inOverviewLayoutThemeOp.isVisible = true
            binding.rvAnimeThemeOp.apply {
                adapter =
                    AnimeOverviewThemeAdapter(viewModel.animeDetails?.openingThemes ?: listOf())
            }
        }

        if (viewModel.animeDetails?.endingThemes?.isNullOrEmpty() == false) {
            binding.inOverviewLayoutThemeEd.isVisible = true
            binding.rvAnimeThemeEd.apply {
                adapter =
                    AnimeOverviewThemeAdapter(viewModel.animeDetails?.endingThemes ?: listOf())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearList()
        binding.rvAnimeOverviewGenre.adapter = null
        binding.rvAnimeOverviewLinks.adapter = null
        binding.rvAnimeOverviewRelation.adapter = null
        binding.rvAnimeThemeEd.adapter = null
        binding.rvAnimeThemeOp.adapter = null
        _binding = null
    }
}