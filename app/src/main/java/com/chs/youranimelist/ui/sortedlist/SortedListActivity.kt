package com.chs.youranimelist.ui.sortedlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.ActivitySortedListBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.browse.BrowseActivity

class SortedListActivity : AppCompatActivity() {
    private lateinit var animeListAdapter: SortedListAdapter
    private lateinit var viewModel: SortedListViewModel
    private var _binding: ActivitySortedListBinding? = null
    private var isLoading: Boolean = false
    private val binding get() = _binding!!
    private val repository by lazy { AnimeListRepository() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySortedListBinding.inflate(layoutInflater)
        viewModel = SortedListViewModel(repository)
        setContentView(binding.root)
        initClick()
        initSortType(intent.getStringExtra("sortType")!!)
        initRecyclerView()
        getAnimeList()
    }

    private fun initClick() {
        binding.animeListYear.setOnClickListener {
            val yearList =
                ArrayList((ConvertDate.getCurrentYear(false) + 1 downTo 1950).map { it.toString() })
            AlertDialog.Builder(this)
                .setItems(yearList.toTypedArray()) { _, which ->
                    if (which == 0) {
                        binding.animeListYear.text = yearList[which]
                    } else {
                        viewModel.selectedYear = yearList[which].toInt()
                        binding.animeListYear.text = viewModel.selectedYear?.toString()
                    }
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSeason.setOnClickListener {
            val seasonArray = viewModel.animeSeasonList.map { it.name }.toTypedArray()
            AlertDialog.Builder(this)
                .setItems(seasonArray) { _, which ->
                    viewModel.selectedSeason = viewModel.animeSeasonList[which]
                    binding.animeListSeason.text = viewModel.selectedSeason?.name
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSort.setOnClickListener {
            AlertDialog.Builder(this)
                .setItems(viewModel.animeSortArray) { _, which ->
                    viewModel.selectedSort = viewModel.animeSortList[which]
                    binding.animeListSort.text = viewModel.animeSortArray[which]
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }
    }


    private fun initSortType(sortType: String) {
        val convertDate: ConvertDate = ConvertDate
        when (sortType) {
            "TRENDING NOW" -> {
                viewModel.selectedSort = MediaSort.TRENDING_DESC
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Trending"
            }
            "POPULAR THIS SEASON" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getCurrentSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(false)
                viewModel.isSeason = true
                binding.animeListYear.text = convertDate.getCurrentYear(false).toString()
                binding.animeListSeason.text = convertDate.getCurrentSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "UPCOMING NEXT SEASON" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getNextSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(true)
                viewModel.isSeason = true
                binding.animeListYear.text = convertDate.getCurrentYear(true).toString()
                binding.animeListSeason.text = convertDate.getNextSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "ALL TIME POPULAR" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Popularity"
            }
        }
    }

    private fun getAnimeList() {
        viewModel.getAnimeList()
        viewModel.animeListResponse.observe(this, {
            when (it.responseState) {
                ResponseState.LOADING -> if (!isLoading) binding.listProgressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    if (isLoading) {
                        viewModel.animeResultList.removeAt(viewModel.animeResultList.lastIndex)
                        animeListAdapter.notifyItemRemoved(viewModel.animeResultList.size)
                        isLoading = false
                    }

                    if (viewModel.isSeason) {
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
                    animeListAdapter.notifyDataSetChanged()
                    binding.listProgressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@SortedListActivity,
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
            animeListAdapter = SortedListAdapter(viewModel.animeResultList) { animeId ->
                val intent = Intent(this@SortedListActivity, BrowseActivity::class.java).apply {
                    this.putExtra("type", "Media")
                    this.putExtra("id", animeId)
                }
                startActivity(intent)
            }
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@SortedListActivity, 3)
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