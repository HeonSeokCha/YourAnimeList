package com.chs.youranimelist.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentSearchBinding
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.dto.SearchResult
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.adapter.SearchAnimeAdapter
import com.chs.youranimelist.ui.search.adapter.SearchCharacterAdapter
import com.chs.youranimelist.ui.search.adapter.SearchMangaAdapter
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val searchKeywordViewModel by activityViewModels<SearchKeywordViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private lateinit var searchMangaAdapter: SearchMangaAdapter
    private lateinit var searchCharaAdapter: SearchCharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.searchPage = arguments?.getString(Constant.TARGET_SEARCH)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        initRecyclerView()
        initView()
        initSearchObserver()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
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


        if (viewModel.searchKeyword.isNotBlank()) {
            viewModel.searchList.clear()

            isLoading = false
            viewModel.page = 1
            viewModel.hasNextPage = true


            viewModel.search(viewModel.searchKeyword)
        }
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.loading()
            viewModel.search(viewModel.searchKeyword)
        }
    }

    private fun initSearchObserver() {
        searchKeywordViewModel.searchKeyword.observe(viewLifecycleOwner) {
            when {
                ::searchAnimeAdapter.isInitialized -> {
                    searchAnimeAdapter.submitList(emptyList<SearchAnimeQuery.Medium?>().toMutableList())
                }
                ::searchMangaAdapter.isInitialized -> {
                    searchMangaAdapter.submitList(emptyList<SearchMangaQuery.Medium?>().toMutableList())
                }
                ::searchCharaAdapter.isInitialized -> {
                    searchCharaAdapter.submitList(emptyList<SearchCharacterQuery.Character?>().toMutableList())
                }
            }
            viewModel.searchKeyword = it
            viewModel.searchList.clear()
            isLoading = false
            viewModel.page = 1
            viewModel.hasNextPage = true
            viewModel.search(it)
        }
    }

    private fun initObserver() {
        viewModel.getObserver()?.observe(viewLifecycleOwner) {
            when (it as NetworkState<*>?) {

                is NetworkState.Loading -> {
                    if (!isLoading) {
                        if (viewModel.searchList.isEmpty()) {
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
                        viewModel.searchList.removeAt(viewModel.searchList.lastIndex)
                        isLoading = false
                    }

                    viewModel.page += 1

                    when (viewModel.searchPage) {

                        Constant.TARGET_ANIME -> {
                            val searchAnime = it as NetworkState<SearchAnimeQuery.Page>
                            viewModel.hasNextPage = searchAnime.data?.pageInfo?.hasNextPage ?: false
                            searchAnime.data?.media?.forEach { anime ->
                                viewModel.searchList.add(SearchResult(animeSearchResult = anime))
                            }
                            searchAnimeAdapter.submitList(searchAnime.data?.media?.toMutableList())
                        }

                        Constant.TARGET_MANGA -> {
                            val searchManga = it as NetworkState<SearchMangaQuery.Page>
                            viewModel.hasNextPage = searchManga.data?.pageInfo?.hasNextPage ?: false
                            searchManga.data?.media?.forEach { manga ->
                                viewModel.searchList.add(SearchResult(mangaSearchResult = manga))
                            }
                            searchMangaAdapter.submitList(searchManga.data?.media?.toMutableList())
                        }

                        Constant.TARGET_CHARA -> {
                            val searchChara = it as NetworkState<SearchCharacterQuery.Page>
                            viewModel.hasNextPage = searchChara.data?.pageInfo?.hasNextPage ?: false
                            searchChara.data?.characters?.forEach { chara ->
                                viewModel.searchList.add(SearchResult(charactersSearchResult = chara))
                            }
                            searchCharaAdapter.submitList(searchChara.data?.characters?.toMutableList())
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
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
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
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
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
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
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