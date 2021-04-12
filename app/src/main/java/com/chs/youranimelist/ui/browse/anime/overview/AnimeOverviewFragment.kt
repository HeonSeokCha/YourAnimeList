package com.chs.youranimelist.ui.browse.anime.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeOverviewBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment


class AnimeOverviewFragment() : BaseFragment() {
    private lateinit var viewModel: AnimeOverviewViewModel
    private lateinit var relationAdapter: AnimeOverviewRelationAdapter
    private lateinit var genreAdapter: AnimeOverviewGenreAdapter
    private lateinit var linkAdapter: AnimeOverviewLinkAdapter
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeOverviewBinding.inflate(inflater, container, false)
        viewModel = AnimeOverviewViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        getAnimeInfo(arguments?.getInt("id")!!)
        initClick()
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

    private fun getAnimeInfo(animeId: Int) {
        viewModel.getAnimeOverview(animeId)
        viewModel.animeOverviewResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> Unit
                ResponseState.SUCCESS -> {
                    binding.model = it.data?.media!!
                    it.data?.media.relations?.relationsEdges?.forEach { relation ->
                        viewModel.animeOverviewRelationList.add(relation)
                    }

                    it.data?.media.genres?.forEach { genres ->
                        viewModel.animeGenresList.add(genres!!)
                    }

                    it.data.media.externalLinks?.forEach { links ->
                        viewModel.animeLinkList.add(links)
                        Log.d("Lniks", links.toString())
                    }
                    relationAdapter.notifyDataSetChanged()
                    genreAdapter.notifyDataSetChanged()
                    linkAdapter.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeOverviewRelation.apply {
            relationAdapter = AnimeOverviewRelationAdapter(viewModel.animeOverviewRelationList,
                clickListener = { _, id ->
                    this@AnimeOverviewFragment.navigate?.changeFragment("Media", id)
                })
            this.adapter = relationAdapter
            this.layoutManager = LinearLayoutManager(
                this@AnimeOverviewFragment.context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        binding.rvAnimeOverviewGenre.apply {
            genreAdapter = AnimeOverviewGenreAdapter(viewModel.animeGenresList)
            this.adapter = genreAdapter
        }

        binding.rvAnimeOverviewLinks.apply {
            linkAdapter = AnimeOverviewLinkAdapter(viewModel.animeLinkList)
            this.adapter = linkAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}