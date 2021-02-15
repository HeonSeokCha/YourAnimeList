package com.chs.youranimelist.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.ActivityAnimeListBinding
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.base.BaseActivity
import com.chs.youranimelist.ui.browse.BrowseActivity
import kotlinx.coroutines.flow.collect

class AnimeListActivity : AppCompatActivity() {

    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var sort: MediaSort
    private lateinit var viewModel: AnimeListViewModel
    private var _binding: ActivityAnimeListBinding? = null
    private var page = 1
    private var mediaSeason: MediaSeason? = null
    private var seasonYear: Int? = null
    private var season: Boolean = false
    private val binding get() = _binding!!
    private val repository by lazy { AnimeListRepository() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimeListBinding.inflate(layoutInflater)
        viewModel = AnimeListViewModel(repository)
        setContentView(binding.root)
        initSortType(intent.getStringExtra("sortType")!!)
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
            seasonYear = seasonYear.toInput()).observe(this,{
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
                            Toast.makeText(this@AnimeListActivity,
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
                val intent = Intent(this@AnimeListActivity, BrowseActivity::class.java).apply {
                    this.putExtra("type","ANIME")
                    this.putExtra("id",animeId)
                }
                startActivity(intent)
            }).apply {
                this.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@AnimeListActivity,3)
            this.addItemDecoration(SpacesItemDecoration(3,8,true))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}