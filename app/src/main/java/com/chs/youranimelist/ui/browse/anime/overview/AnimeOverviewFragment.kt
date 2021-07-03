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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeOverviewBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository


class AnimeOverviewFragment : Fragment() {
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimeOverviewViewModel
    private lateinit var relationAdapter: AnimeOverviewRelationAdapter
    private lateinit var genreAdapter: AnimeOverviewGenreAdapter
    private lateinit var linkAdapter: AnimeOverviewLinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeOverviewViewModel(repository)
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
        viewModel.getAnimeOverview(arguments?.getInt("id")!!)
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
    }

    private fun getAnimeInfo() {
        if (arguments?.getInt("idMal")!! != 0) {
            viewModel.getAnimeTheme(arguments?.getInt("idMal")!!)
        }

        viewModel.animeOverviewResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.SUCCESS -> {

                    binding.model = it.data?.media!!

                    it.data.media.relations?.relationsEdges?.forEach { relation ->
                        viewModel.animeOverviewRelationList.add(relation)
                    }

                    it.data.media.genres?.forEach { genres ->
                        viewModel.animeGenresList.add(genres!!)
                    }

                    it.data.media.externalLinks?.forEach { links ->
                        viewModel.animeLinkList.add(links)
                    }

                    relationAdapter?.notifyDataSetChanged()
                    genreAdapter?.notifyDataSetChanged()
                    linkAdapter?.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.animeThemeResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.SUCCESS -> {
                    viewModel.animeDetails = it.data
                    initAnimeTheme()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeOverviewRelation.apply {
            relationAdapter =
                AnimeOverviewRelationAdapter(viewModel.animeOverviewRelationList) { id, idMal ->
                    val action =
                        AnimeOverviewFragmentDirections.actionAnimeOverviewFragmentToAnimeDetailFragment(
                            id,
                            idMal
                        )
                    findNavController().navigate(action)
                }
            this.adapter = relationAdapter
            this.layoutManager = LinearLayoutManager(
                this@AnimeOverviewFragment.context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        binding.rvAnimeOverviewGenre.apply {
            genreAdapter = AnimeOverviewGenreAdapter(viewModel.animeGenresList) {
                val action =
                    AnimeOverviewFragmentDirections.actionAnimeOverviewFragmentToSortedFragment()
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
    }

    private fun initAnimeTheme() {
        if (viewModel.animeDetails?.openingThemes?.isNullOrEmpty() == false) {
            binding.txtAnimeThemeOp.isVisible = true
            binding.rvAnimeThemeOp.apply {
                isVisible = true
                adapter =
                    AnimeOverviewThemeAdapter(viewModel.animeDetails?.openingThemes ?: listOf())
            }
        }

        if (viewModel.animeDetails?.endingThemes?.isNullOrEmpty() == false) {
            binding.txtAnimeThemeEd.isVisible = true
            binding.rvAnimeThemeEd.apply {
                isVisible = true
                adapter =
                    AnimeOverviewThemeAdapter(viewModel.animeDetails?.endingThemes ?: listOf())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeOverviewGenre.adapter = null
        binding.rvAnimeOverviewLinks.adapter = null
        binding.rvAnimeOverviewRelation.adapter = null
        binding.rvAnimeThemeEd.adapter = null
        binding.rvAnimeThemeOp.adapter = null
        _binding = null
    }
}