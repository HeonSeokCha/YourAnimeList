package com.chs.youranimelist.ui.browse.anime.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.databinding.FragmentAnimeOverviewBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.base.BaseNavigator
import com.chs.youranimelist.ui.main.MainViewModel


class AnimeOverviewFragment(private val animeInfo: AnimeDetailQuery.Media) : BaseFragment() {
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeOverviewBinding.inflate(inflater, container, false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.model = animeInfo
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvAnimeOverviewRelation.apply {
            this.adapter = AnimeOverviewRelationAdapter(animeInfo.relations?.relationsEdges!!,
                clickListener = { type, id ->
                    this@AnimeOverviewFragment.navigate?.changeFragment("ANIME", id)
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