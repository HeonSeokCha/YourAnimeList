package com.chs.youranimelist.ui.sortedlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentSortedBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.main.MainActivity
import com.chs.youranimelist.util.ConvertDate
import com.chs.youranimelist.util.SpacesItemDecoration

class SortedFragment : BaseFragment() {
    private var _binding: FragmentSortedBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private lateinit var animeListAdapter: SortedListAdapter
    private lateinit var viewModel: SortedListViewModel
    private val repository by lazy { AnimeListRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortedBinding.inflate(inflater, container, false)
        viewModel = SortedListViewModel(repository)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        initSortType(arguments?.getString("sortType")!!)
        initRecyclerView()
        viewModel.getAnimeList()
        getAnimeList()
    }

    private fun initClick() {
        binding.animeListYear.setOnClickListener {
            val yearList =
                ArrayList((ConvertDate.getCurrentYear(false) + 1 downTo 1970).map { it.toString() })
            AlertDialog.Builder(this.context!!)
                .setItems(yearList.toTypedArray()) { _, which ->
                    if (which == 0) {
                        binding.animeListYear.text = yearList[which]
                        viewModel.selectStatus = MediaStatus.NOT_YET_RELEASED
                    } else {
                        viewModel.selectedYear = yearList[which].toInt()
                        viewModel.selectStatus = null
                        binding.animeListYear.text = viewModel.selectedYear?.toString()
                    }
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSeason.setOnClickListener {
            val seasonArray = viewModel.animeSeasonList.map { it.name }.toTypedArray()
            AlertDialog.Builder(this.context!!)
                .setItems(seasonArray) { _, which ->
                    viewModel.selectedSeason = viewModel.animeSeasonList[which]
                    binding.animeListSeason.text = viewModel.selectedSeason?.name
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSort.setOnClickListener {
            AlertDialog.Builder(this.context!!)
                .setItems(viewModel.animeSortArray) { _, which ->
                    viewModel.selectedSort = viewModel.animeSortList[which]
                    binding.animeListSort.text = viewModel.animeSortArray[which]
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter.notifyDataSetChanged()
                }
                .show()
        }
    }


    private fun initSortType(sortType: String) {
        when (sortType) {
            "TRENDING NOW" -> {
                viewModel.selectedSort = MediaSort.TRENDING_DESC
                viewModel.isSeason = false
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Trending"
            }
            "POPULAR THIS SEASON" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getCurrentSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(false)
                viewModel.isSeason = true
                binding.animeListYear.text = ConvertDate.getCurrentYear(false).toString()
                binding.animeListSeason.text = ConvertDate.getCurrentSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "UPCOMING NEXT SEASON" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getNextSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(true)
                viewModel.isSeason = true
                binding.animeListYear.text = ConvertDate.getCurrentYear(true).toString()
                binding.animeListSeason.text = ConvertDate.getNextSeason().toString()
                binding.animeListSort.text = "Popularity"
            }
            "ALL TIME POPULAR" -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.isSeason = false
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "Popularity"
            }
        }
    }

    private fun getAnimeList() {
        viewModel.animeListResponse.observe(viewLifecycleOwner, {
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
                        it.data.nonSeason.media?.forEach { nonSeasonAnime ->
                            viewModel.animeResultList.add(nonSeasonAnime!!.fragments.animeList)
                        }
                    }
                    animeListAdapter.notifyDataSetChanged()
                    binding.listProgressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@SortedFragment.context,
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
            animeListAdapter = SortedListAdapter(viewModel.animeResultList) { id, idMal ->
                val intent = Intent(this@SortedFragment.context, BrowseActivity::class.java).apply {
                    this.putExtra("type", "Media")
                    this.putExtra("id", id)
                    this.putExtra("idMal", idMal)
                }
                startActivity(intent)
            }
            animeListAdapter.setHasStableIds(true)
            this.adapter = animeListAdapter
            this.layoutManager = GridLayoutManager(this@SortedFragment.context, 3)
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
            viewModel.page += 1
            viewModel.getAnimeList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}