package com.chs.youranimelist.ui.browse.anime.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
        Log.d("argument", "${arguments?.getInt("id")}")
        initView()
        initClick()
    }

    private fun getAnimeInfo(animeId: Int) {
        viewModel.getAnimeOverview(animeId).observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> {
                }
                ResponseState.SUCCESS -> {
                    binding.model = it.data!!
                    initRecyclerView(it.data!!)
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initView() {
        getAnimeInfo(arguments?.getInt("id")!!)
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

    private fun initRecyclerView(animeInfo: AnimeOverviewQuery.Media) {
        binding.rvAnimeOverviewRelation.apply {
            this.adapter = AnimeOverviewRelationAdapter(animeInfo.relations?.relationsEdges!!,
                clickListener = { _, id ->
                    this@AnimeOverviewFragment.navigate?.changeFragment("Media", id)
                })
            this.layoutManager = LinearLayoutManager(
                this@AnimeOverviewFragment.context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        binding.rvAnimeOverviewGenre.apply {
            this.adapter = AnimeOverviewGenreAdapter(animeInfo.genres!!)
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