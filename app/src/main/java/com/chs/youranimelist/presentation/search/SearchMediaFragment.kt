package com.chs.youranimelist.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.databinding.FragmentSearchMediaBinding
import com.chs.youranimelist.presentation.base.BaseFragment
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.search.adapter.SearchAnimeAdapter
import com.chs.youranimelist.presentation.search.adapter.SearchCharacterAdapter
import com.chs.youranimelist.presentation.search.adapter.SearchMangaAdapter
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMediaFragment : BaseFragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val parentViewModel: SearchKeywordViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var _binding: FragmentSearchMediaBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private lateinit var searchMangaAdapter: SearchMangaAdapter
    private lateinit var searchCharaAdapter: SearchCharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchPage = requireArguments().getString(Constant.TARGET_SEARCH)!!
        setHasOptionsMenu(false)
        initRecyclerView()
        initView()
        initObserver()
    }

    private fun initView() {
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.loading()
            viewModel.search(viewModel.searchKeyword)
        }
    }

    private fun initObserver() {
        parentViewModel.searchKeyword.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                when (viewModel.searchPage) {
                    Constant.TARGET_ANIME -> searchAnimeAdapter.submitList(emptyList())
                    Constant.TARGET_MANGA -> searchMangaAdapter.submitList(emptyList())
                    Constant.TARGET_CHARA -> searchCharaAdapter.submitList(emptyList())
                }
                viewModel.searchKeyword = it
                viewModel.clear()
                isLoading = false
                viewModel.page = 1
                viewModel.hasNextPage = true
                viewModel.search(viewModel.searchKeyword)
            }
        }

        viewModel.getObserver()?.observe(viewLifecycleOwner) {
            when (it as NetworkState<*>?) {

                is NetworkState.Loading -> {
                    if (!isLoading) {
                        if (viewModel.isSearchEmpty()) {
                            binding.layoutShimmerSearch.root.isVisible = true
                        }
                        binding.imgSearchError.isVisible = false
                        binding.txtSearchError.isVisible = false
                    }
                }

                is NetworkState.Success -> {
                    if (!viewModel.hasNextPage) {
                        return@observe
                    }

                    if (isLoading) {
                        viewModel.finishLoading()
                        isLoading = false
                    }

                    viewModel.page += 1

                    when (viewModel.searchPage) {
                        Constant.TARGET_ANIME -> {
                            val searchAnime = it as NetworkState<SearchAnimeQuery.Page>
                            viewModel.hasNextPage = searchAnime.data?.pageInfo?.hasNextPage ?: false
                            searchAnime.data?.media?.forEach { anime ->
                                viewModel.searchAnimeList.add(anime)
                            }
                            searchAnimeAdapter.submitList(viewModel.searchAnimeList.toMutableList())
                        }

                        Constant.TARGET_MANGA -> {
                            val searchManga = it as NetworkState<SearchMangaQuery.Page>
                            viewModel.hasNextPage = searchManga.data?.pageInfo?.hasNextPage ?: false
                            searchManga.data?.media?.forEach { manga ->
                                viewModel.searchMangaList.add(manga)
                            }
                            searchMangaAdapter.submitList(viewModel.searchMangaList.toMutableList())
                        }

                        Constant.TARGET_CHARA -> {
                            val searchChara = it as NetworkState<SearchCharacterQuery.Page>
                            viewModel.hasNextPage = searchChara.data?.pageInfo?.hasNextPage ?: false
                            searchChara.data?.characters?.forEach { chara ->
                                viewModel.searchCharaList.add(chara)
                            }
                            searchCharaAdapter.submitList(viewModel.searchCharaList.toMutableList())
                        }
                    }

                    binding.layoutShimmerSearch.root.isVisible = false
                    binding.rvSearch.isVisible = true
                }
                is NetworkState.Error -> {
                    isLoading = false
                    binding.layoutShimmerSearch.root.isVisible = false
                    binding.imgSearchError.isVisible = true
                    binding.txtSearchError.isVisible = true
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvSearch.adapter = when (viewModel.searchPage) {
            Constant.TARGET_ANIME -> {
                searchAnimeAdapter = SearchAnimeAdapter { id, idMal ->
                    val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                        this.putExtra(Constant.TARGET_ID, id)
                        this.putExtra(Constant.TARGET_ID_MAL, idMal)
                    }
                    startActivity(intent)
                }
                searchAnimeAdapter
            }
            Constant.TARGET_MANGA -> {
                searchMangaAdapter = SearchMangaAdapter { id, idMal ->
                    val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                        this.putExtra(Constant.TARGET_ID, id)
                        this.putExtra(Constant.TARGET_ID_MAL, idMal)
                    }
                    startActivity(intent)
                }
                searchMangaAdapter
            }
            Constant.TARGET_CHARA -> {
                searchCharaAdapter = SearchCharacterAdapter { id ->
                    val intent = Intent(requireContext(), BrowseActivity::class.java).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_CHARA)
                        this.putExtra(Constant.TARGET_ID, id)
                    }
                    startActivity(intent)
                }
                searchCharaAdapter
            }
            else -> {
                SearchMangaAdapter { _, _ -> }
            }
        }
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearch.adapter = null
        _binding = null
    }
}