package com.chs.youranimelist.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.ActivityAnimeListBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.ResponseState
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
    private var page: Int = 1
    private var isLoading: Boolean = false
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
        getAnimeList()
        initRecyclerView()
    }

    private fun initSortType(sortType: String) {
        when (sortType) {
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
        viewModel.getAnimeList(
            sort = sort.toInput(),
            season = mediaSeason,
            seasonYear = seasonYear.toInput()
        ).observe(this, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.listProgressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    if (isLoading) {
                        viewModel.animeResultList.removeAt(viewModel.animeResultList.lastIndex)
                        animeListAdapter.notifyItemRemoved(viewModel.animeResultList.size)
                        isLoading = false
                    }

                    viewModel.page += 1

                    if (season) {
                        viewModel.hasNextPage =
                            it.data!!.season!!.fragments.pageInfo.pageInfo!!.hasNextPage ?: false
                        it.data?.season?.media?.forEach { seasonAnime ->
                            viewModel.animeResultList.add(seasonAnime!!.fragments.animeList)
                        }
                    } else {
                        viewModel.hasNextPage =
                            it.data!!.nonSeason!!.fragments.pageInfo.pageInfo!!.hasNextPage ?: false
                        it.data?.nonSeason?.media?.forEach { nonSeasonAnime ->
                            viewModel.animeResultList.add(nonSeasonAnime!!.fragments.animeList)
                        }
                    }
                    animeListAdapter.notifyDataSetChanged()
                    binding.listProgressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@AnimeListActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.listProgressBar.isVisible = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeList.apply {
            animeListAdapter =
                AnimeListAdapter(viewModel.animeResultList, clickListener = { animeId ->
                    val intent = Intent(this@AnimeListActivity, BrowseActivity::class.java).apply {
                        this.putExtra("type", "Media")
                        this.putExtra("id", animeId)
                    }
                    startActivity(intent)
                }).apply {
                    this.stateRestorationPolicy =
                        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@AnimeListActivity, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        !recyclerView.canScrollVertically(1) && !isLoading
                    ) {
                        loadMore()
                        isLoading = true
                    }
                }
            })
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.animeResultList.add(null)
            animeListAdapter.notifyItemInserted(viewModel.animeResultList.lastIndex)

        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}