package com.chs.youranimelist.presentation.sortedlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentSortedBinding
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.presentation.base.BaseFragment
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.main.MainActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.ConvertDate
import com.chs.youranimelist.util.SpacesItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortedFragment : BaseFragment() {
    private var _binding: FragmentSortedBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var animeListAdapter: SortedListAdapter? = null
    private var filterListAdapter: SortedFilterAdapter? = null
    private val viewModel: SortedListViewModel by viewModels()
    private val args: SortedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSortType(args.sortType)
        initRecyclerView()
        getAnimeList()
        getGenre()
        viewModel.getAnimeList()
        viewModel.getGenreList()
        setHasOptionsMenu(true)
    }

    private fun initFilterClick(filterName: String) {
        when (filterName) {
            "Year" -> {
                val yearList =
                    ArrayList((ConvertDate.getCurrentYear(true) downTo 1970).map { it.toString() })
                MaterialAlertDialogBuilder(requireContext())
                    .setItems(yearList.toTypedArray()) { _, which ->
                        viewModel.selectedYear = yearList[which].toInt()
                        viewModel.filterList[0] = yearList[which].toString()
                        if (viewModel.selectedSeason != null) {
                            viewModel.selectType = Constant.SEASON_YEAR
                        } else {
                            viewModel.selectType = Constant.NO_SEASON
                        }
                        isLoading = false
                        viewModel.refresh()
                        filterListAdapter?.notifyItemChanged(0)
                        animeListAdapter?.submitList(mutableListOf())
                        getAnimeList()
                    }.show()
            }

            "Season" -> {
                val seasonArray = Constant.animeSeasonList.map { it.name }.toTypedArray()
                MaterialAlertDialogBuilder(requireContext())
                    .setItems(seasonArray) { _, which ->
                        viewModel.isSeason = true
                        if (viewModel.selectedYear == null) {
                            viewModel.selectedYear = ConvertDate.getCurrentYear(false)
                        }
                        viewModel.selectedSeason = Constant.animeSeasonList[which]
                        viewModel.selectType = Constant.SEASON_YEAR
                        isLoading = false
                        viewModel.refresh()
                        viewModel.filterList[1] = Constant.animeSeasonList[which].toString()
                        filterListAdapter?.notifyItemChanged(1)
                        animeListAdapter?.submitList(mutableListOf())
                        getAnimeList()
                    }
                    .show()
            }

            "Sort" -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setItems(Constant.animeSortArray) { _, which ->
                        viewModel.selectedSort = Constant.animeSortList[which]
                        isLoading = false
                        viewModel.refresh()
                        viewModel.filterList[2] = Constant.animeSortArray[which]
                        filterListAdapter?.notifyItemChanged(2)
                        animeListAdapter?.submitList(mutableListOf())
                        getAnimeList()
                    }.show()
            }

            "Genre" -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setItems(viewModel.genreList.toTypedArray()) { _, which ->
                        viewModel.selectedSort = MediaSort.POPULARITY_DESC
                        viewModel.selectGenre = viewModel.genreList[which]
                        isLoading = false
                        viewModel.refresh()
                        viewModel.filterList[0] = viewModel.genreList[which]
                        filterListAdapter?.notifyItemChanged(0)
                        animeListAdapter?.submitList(mutableListOf())
                        getAnimeList()
                    }.show()
            }
        }
    }


    private fun initSortType(sortType: String) {
        when (sortType) {
            Constant.TRENDING_NOW -> {
                viewModel.selectedSort = MediaSort.TRENDING_DESC
                viewModel.selectType = Constant.NO_SEASON_NO_YEAR
                viewModel.initFilterList(
                    "Any",
                    "Any",
                    "Trending"
                )
            }
            Constant.POPULAR_THIS_SEASON -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getCurrentSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(false)
                viewModel.selectType = Constant.SEASON_YEAR
                viewModel.initFilterList(
                    ConvertDate.getCurrentYear(false).toString(),
                    ConvertDate.getCurrentSeason().toString(),
                    "Popularity"
                )
            }
            Constant.UPCOMING_NEXT_SEASON -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = ConvertDate.getNextSeason()
                viewModel.selectedYear = ConvertDate.getCurrentYear(true)
                viewModel.selectType = Constant.SEASON_YEAR
                viewModel.initFilterList(
                    ConvertDate.getCurrentYear(true).toString(),
                    ConvertDate.getCurrentSeason().toString(),
                    "Popularity"
                )
            }
            Constant.ALL_TIME_POPULAR -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectType = Constant.NO_SEASON_NO_YEAR
                viewModel.initFilterList(
                    "Any",
                    "Any",
                    "Popularity"
                )
            }
            Constant.TARGET_GENRE -> {
                viewModel.selectType = Constant.NO_SEASON_NO_YEAR
                viewModel.selectedSort = MediaSort.SCORE_DESC
                viewModel.selectGenre = args.genre
                viewModel.initFilterList(
                    genre = args.genre
                )
            }
            Constant.TARGET_SEASON -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = args.season
                viewModel.selectedYear = args.year
                viewModel.selectType = Constant.SEASON_YEAR
                viewModel.initFilterList(
                    args.year.toString(),
                    args.season.toString(),
                    "Popularity"
                )
            }
        }
        filterListAdapter?.notifyDataSetChanged()
    }

    private fun getAnimeList() {
        viewModel.getObserver()?.observe(viewLifecycleOwner) {
            when (it as NetworkState<*>?) {
                is NetworkState.Loading -> {
                    if (!isLoading)
                        binding.layoutShimmerSorted.root.isVisible = true
                }
                is NetworkState.Success -> {
                    if (!viewModel.hasNextPage) {
                        return@observe
                    }

                    if (isLoading) {
                        viewModel.animeResultList.removeAt(viewModel.animeResultList.lastIndex)
                        isLoading = false
                    }

                    when (viewModel.selectType) {
                        Constant.SEASON_YEAR -> {
                            val seasonYear = it as NetworkState<AnimeListQuery.Page>
                            viewModel.hasNextPage = seasonYear.data?.pageInfo?.hasNextPage ?: false
                            seasonYear.data?.media?.forEach { anime ->
                                viewModel.animeResultList.add(anime?.fragments?.animeList)
                            }
                        }
                        Constant.NO_SEASON_NO_YEAR -> {
                            val noSeasonNoYear = it as NetworkState<NoSeasonNoYearQuery.Page>
                            viewModel.hasNextPage =
                                noSeasonNoYear.data?.pageInfo?.hasNextPage ?: false
                            noSeasonNoYear.data?.media?.forEach { anime ->
                                viewModel.animeResultList.add(anime?.fragments?.animeList)
                            }
                        }
                        Constant.NO_SEASON -> {
                            val noSeason = it as NetworkState<NoSeasonQuery.Page>
                            viewModel.hasNextPage =
                                noSeason.data?.pageInfo?.hasNextPage ?: false
                            noSeason.data?.media?.forEach { anime ->
                                viewModel.animeResultList.add(anime?.fragments?.animeList)
                            }
                        }
                    }
                    binding.layoutShimmerSorted.root.isVisible = false
                    binding.rvAnimeList.isVisible = true
                    filterListAdapter?.notifyDataSetChanged()
                    animeListAdapter?.submitList(viewModel.animeResultList.toMutableList())
                }
                is NetworkState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    isLoading = false
                    binding.layoutShimmerSorted.root.isVisible = false
                }
                else -> {}
            }
        }
    }

    private fun getGenre() {
        viewModel.genreListResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Success -> {
                    it.data?.genreCollection?.forEach { genre ->
                        if (genre != null) {
                            viewModel.genreList.add(genre)
                        }
                    }
                }

                is NetworkState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimeList.apply {
            animeListAdapter = SortedListAdapter { id, idMal ->
                val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                    this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                    this.putExtra(Constant.TARGET_ID, id)
                    this.putExtra(Constant.TARGET_ID_MAL, idMal)
                }
                startActivity(intent)
            }
            this.adapter = animeListAdapter
            this.setHasFixedSize(true)
            this.layoutManager = GridLayoutManager(requireContext(), 3)
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

        binding.rvSortedFilter.apply {
            filterListAdapter = SortedFilterAdapter(viewModel.filterList) {
                initFilterClick(it)
            }
            this.adapter = filterListAdapter
            this.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.animeResultList.add(null)
            animeListAdapter?.notifyItemInserted(viewModel.animeResultList.lastIndex)
            viewModel.page += 1
            viewModel.getAnimeList()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        binding.rvAnimeList.adapter = null
        viewModel.clear()
        _binding = null
    }
}