package com.chs.youranimelist.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.main.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect


class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimeListViewModel
    private val repository by lazy { AnimeListRepository() }
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
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        viewModel = AnimeListViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initSortType(arguments?.getString("sortType")!!)
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
                 mediaSeason = ConvertDate.getCurrentSeason()
                 seasonYear = ConvertDate.getCurrentYear(false)
                 season = true
             }
            "UPCOMING NEXT SEASON" -> {
                sort = MediaSort.POPULARITY_DESC
                mediaSeason = ConvertDate.getNextSeason()
                seasonYear = ConvertDate.getCurrentYear(true)
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
            season = mediaSeason,
            seasonYear = seasonYear.toInput()).observe(viewLifecycleOwner,{
            lifecycleScope.launchWhenStarted {
                viewModel.netWorkState.collect { netWorkState ->
                    when(netWorkState) {
                        is AnimeListViewModel.NetWorkState.Success -> {
                            if(season) {
                                animeListAdapter.submitList(it)
                            } else {
                                animeListAdapter.submitList(it)
                            }
                            binding.listProgressBar.isVisible = false
                        }
                        is AnimeListViewModel.NetWorkState.Error -> {
                            Toast.makeText(
                                this@AnimeListFragment.context,
                                netWorkState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.listProgressBar.isVisible = false
                        }
                        is AnimeListViewModel.NetWorkState.Loading -> {
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
                val intent = Intent(activity,BrowseActivity::class.java).apply {
                    this.putExtra("id",animeId)
                    this.putExtra("browseType","ANIME")
                }
                startActivity(intent)
            }).apply {
                this.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@AnimeListFragment.context,3)
            this.addItemDecoration(SpacesItemDecoration(3,8,true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}