package com.chs.youranimelist.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.ActivityAnimeListBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.browse.BrowseActivity

class AnimeListActivity : AppCompatActivity() {
    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var sort: MediaSort
    private lateinit var viewModel: AnimeListViewModel
    private var _binding: ActivityAnimeListBinding? = null
    private var isLoading: Boolean = false
    private var mediaSeason: MediaSeason? = null
    private var seasonYear: Int = 0
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
        val convertDate: ConvertDate = ConvertDate
        when (sortType) {
            "TRENDING NOW" -> {
                sort = MediaSort.TRENDING_DESC
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Trending"
            }
            "POPULAR THIS SEASON" -> {
                sort = MediaSort.POPULARITY_DESC
                mediaSeason = ConvertDate.getCurrentSeason()
                seasonYear = ConvertDate.getCurrentYear(false)
                season = true
                binding.animeListYear.text = convertDate.getCurrentYear(false).toString()
                binding.animeListSeason.text = convertDate.getCurrentSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "UPCOMING NEXT SEASON" -> {
                sort = MediaSort.POPULARITY_DESC
                mediaSeason = ConvertDate.getNextSeason()
                seasonYear = ConvertDate.getCurrentYear(true)
                season = true
                binding.animeListYear.text = convertDate.getCurrentYear(true).toString()
                binding.animeListSeason.text = convertDate.getNextSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "ALL TIME POPULAR" -> {
                sort = MediaSort.POPULARITY_DESC
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Popularity"
            }
        }
    }

    private fun getAnimeList() {

        viewModel.getAnimeList(
            sort = sort,
            season = mediaSeason,
            seasonYear = seasonYear
        )

        viewModel.animeListResponse.observe(this, {
            when (it.responseState) {
                ResponseState.LOADING -> if (!isLoading) binding.listProgressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    if (isLoading) {
                        viewModel.animeResultList.removeAt(viewModel.animeResultList.lastIndex)
                        animeListAdapter.notifyItemRemoved(viewModel.animeResultList.size)
                        isLoading = false
                    }

                    if (season) {
                        viewModel.hasNextPage =
                            it.data?.season?.pageInfo?.hasNextPage ?: false
                        it.data?.season?.media?.forEach { seasonAnime ->
                            viewModel.animeResultList.add(seasonAnime!!.fragments.animeList)
                        }
                    } else {
                        viewModel.hasNextPage =
                            it.data?.nonSeason?.pageInfo!!.hasNextPage ?: false
                        it.data?.nonSeason?.media?.forEach { nonSeasonAnime ->
                            viewModel.animeResultList.add(nonSeasonAnime!!.fragments.animeList)
                        }
                    }
                    animeListAdapter.submitList(viewModel.animeResultList)
                    binding.listProgressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@AnimeListActivity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    isLoading = false
                    binding.listProgressBar.isVisible = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeList.apply {
            animeListAdapter =
                AnimeListAdapter(clickListener = { animeId ->
                    val intent = Intent(this@AnimeListActivity, BrowseActivity::class.java).apply {
                        this.putExtra("type", "Media")
                        this.putExtra("id", animeId)
                    }
                    startActivity(intent)
                })
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@AnimeListActivity, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))

            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        !recyclerView.canScrollVertically(1) && !isLoading
                    ) {
                        isLoading = true
                        loadMore()
                    }
                }
            })
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.animeResultList.add(null)
            animeListAdapter.notifyItemInserted(viewModel.animeResultList.lastIndex)
            viewModel.page += 1
            getAnimeList()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}