package com.chs.youranimelist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.adapter.AnimeListAdapter
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect


class AnimeListFragment : Fragment() {
    private lateinit var binding: FragmentAnimeListBinding
    private lateinit var viewModel: MainViewModel
    private val args: AnimeListFragmentArgs by navArgs()
    private val repository by lazy { AnimeRepository() }
    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var sort:MediaSort
    private var page = 1
    private var mediaSeason: MediaSeason? = null
    private var seasonYear: Int? = null
    private var season: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).binding.toolbar.title = args.sortType
        initSortType(args.sortType)
        initRecyclerView()
        getAnimeList()
    }

    private fun initSortType(sortType: String) {
        when(sortType) {
            "TRENDING NOW" -> {
                sort = MediaSort.TRENDING_DESC
             }
             "POPULAR THIS SEASON" -> {
                 sort = MediaSort.POPULARITY_DESC
                 mediaSeason = MediaSeason.WINTER
                 seasonYear = 2021
                 season = true
             }
            "UPCOMING NEXT SEASON" -> {
                sort = MediaSort.POPULARITY_DESC
                mediaSeason = MediaSeason.SPRING
                seasonYear = 2021
                season = true
            }
            "ALL TIME POPULAR" -> {
                sort = MediaSort.POPULARITY_DESC
            }
        }
    }

    private fun getAnimeList() {
        viewModel.getAnimeList(page = page.toInput(),
            sort = sort.toInput(),
            season = mediaSeason.toInput(),
            seasonYear = seasonYear.toInput()).observe(viewLifecycleOwner,{
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when(netWorkState) {
                        is MainViewModel.NetWorkState.Success -> {
                            if(season) {
                                animeListAdapter.submitList(it.season?.media)
                            } else {
                                animeListAdapter.submitList(it.nonSeason?.media)
                            }
                            binding.listProgressBar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@AnimeListFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.listProgressBar.isVisible = false
                        }
                        is MainViewModel.NetWorkState.Loading -> {
                            binding.listProgressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeList.apply {
            animeListAdapter = AnimeListAdapter(clickListener = { animeId ->
                val action = AnimeListFragmentDirections.actionAnimeListFragmentToAnimeDetailFragment(animeId)
                binding.root.findNavController().navigate(action)
            })
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@AnimeListFragment.context,3)
        }
    }
}