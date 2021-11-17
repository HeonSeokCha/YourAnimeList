package com.chs.youranimelist.ui.sortedlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentSortedBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.type.MediaStatus
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.main.MainActivity
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.ConvertDate
import com.chs.youranimelist.util.SpacesItemDecoration
import kotlinx.coroutines.flow.collectLatest

class SortedFragment : BaseFragment() {
    private var _binding: FragmentSortedBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var animeListAdapter: SortedListAdapter? = null
    private val viewModel by viewModels<SortedListViewModel>()
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
        initClick()
        initSortType(args.sortType)
        initRecyclerView()
        viewModel.getAnimeList()
        viewModel.getGenreList()
        getAnimeList()
        getGenre()
        setHasOptionsMenu(true)
    }

    private fun initClick() {
        binding.animeListYear.setOnClickListener {
            val yearList =
                ArrayList((ConvertDate.getCurrentYear(false) + 1 downTo 1970).map { it.toString() })
            AlertDialog.Builder(this.requireContext())
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
                    animeListAdapter?.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSeason.setOnClickListener {
            val seasonArray = Constant.animeSeasonList.map { it.name }.toTypedArray()
            AlertDialog.Builder(this.requireContext())
                .setItems(seasonArray) { _, which ->
                    viewModel.isSeason = true
                    viewModel.selectedSeason = Constant.animeSeasonList[which]
                    binding.animeListSeason.text = viewModel.selectedSeason?.name
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter?.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListSort.setOnClickListener {
            AlertDialog.Builder(this.requireContext())
                .setItems(Constant.animeSortArray) { _, which ->
                    viewModel.selectedSort = Constant.animeSortList[which]
                    binding.animeListSort.text = Constant.animeSortArray[which]
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter?.notifyDataSetChanged()
                }
                .show()
        }

        binding.animeListGenre.setOnClickListener {
            AlertDialog.Builder(this.requireContext())
                .setItems(viewModel.genreList.toTypedArray()) { _, which ->
                    viewModel.selectedSort = MediaSort.POPULARITY_DESC
                    viewModel.selectGenre = viewModel.genreList[which]
                    binding.animeListGenre.text = viewModel.genreList[which]
                    isLoading = false
                    viewModel.refresh()
                    animeListAdapter?.notifyDataSetChanged()
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
            Constant.TARGET_GENRE -> {
                binding.horizontalFilterScrollView.isVisible = false
                binding.horizontalTagScrollView.isVisible = true
                binding.animeListGenre.text = args.genre
                viewModel.selectedSort = MediaSort.SCORE_DESC
                viewModel.isSeason = false
                binding.animeListYear.text = "Any"
                binding.animeListSeason.text = "Any"
                binding.animeListSort.text = "AverageScore"
                viewModel.selectGenre = args.genre
            }
            Constant.TARGET_SEASON -> {
                viewModel.selectedSort = MediaSort.POPULARITY_DESC
                viewModel.selectedSeason = args.season
                viewModel.selectedYear = args.year
                viewModel.isSeason = true
                binding.animeListYear.text = args.year.toString()
                binding.animeListSeason.text = args.season.toString()
                binding.animeListSort.text = "Popularity"
            }
        }
    }

    private fun getAnimeList() {
        lifecycleScope.launchWhenStarted {
            viewModel.animeListResponse.collectLatest {
                when (it.responseState) {
                    ResponseState.LOADING -> {
                        if (!isLoading)
                            binding.layoutShimmerSorted.root.isVisible = true
                    }
                    ResponseState.SUCCESS -> {
                        if (!viewModel.hasNextPage) {
                            return@collectLatest
                        }

                        if (isLoading) {
                            viewModel.animeResultList.removeAt(viewModel.animeResultList.lastIndex)
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
                        animeListAdapter?.notifyItemRangeChanged(((viewModel.page * 10)), 10)
                        binding.layoutShimmerSorted.root.isVisible = false
                        binding.rvAnimeList.isVisible = true
                    }
                    ResponseState.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                        isLoading = false
                        binding.layoutShimmerSorted.root.isVisible = false
                    }
                }
            }
        }
    }

    private fun getGenre() {
        lifecycleScope.launchWhenStarted {
            viewModel.genreListResponse.collectLatest {
                when (it.responseState) {
                    ResponseState.SUCCESS -> {
                        it.data?.genreCollection?.forEach { genre ->
                            if (genre != null) {
                                viewModel.genreList.add(genre)
                            }
                        }
                    }

                    ResponseState.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimeList.apply {
            animeListAdapter = SortedListAdapter(viewModel.animeResultList) { id, idMal ->
                val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                    this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                    this.putExtra(Constant.TARGET_ID, id)
                    this.putExtra(Constant.TARGET_ID_MAL, idMal)
                }
                startActivity(intent)
            }
            animeListAdapter?.setHasStableIds(true)
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