package com.chs.youranimelist.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.databinding.FragmentSearchBinding
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.response.SearchResult
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.adapter.SearchAnimeAdapter
import com.chs.youranimelist.ui.search.adapter.SearchCharacterAdapter
import com.chs.youranimelist.ui.search.adapter.SearchMangaAdapter
import com.chs.youranimelist.util.Constant

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: RecyclerView.Adapter<*>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private val repository by lazy { SearchRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = SearchViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchPage = arguments?.getString(Constant.TARGET_SEARCH)!!
        initRecyclerView()
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.getObserver()?.observe(viewLifecycleOwner, {
            when ((it as NetWorkState<*>?)?.responseState) {

                ResponseState.LOADING -> binding.progressBar.isVisible = true

                ResponseState.SUCCESS -> {
                    if (isLoading) {
                        viewModel.searchList.removeAt(viewModel.searchList.lastIndex)
                        adapter.notifyItemRemoved(viewModel.searchList.size)
                        isLoading = false
                    }

                    viewModel.page += 1

                    when (viewModel.searchPage) {
                        Constant.TARGET_ANIME -> {
                            val searchAnime = it as NetWorkState<SearchAnimeQuery.Page>
                            viewModel.hasNextPage = searchAnime.data?.pageInfo?.hasNextPage ?: false
                            searchAnime.data?.media?.forEach { anime ->
                                viewModel.searchList.add(SearchResult(animeSearchResult = anime))
                            }
                        }
                        Constant.TARGET_MANGA -> {
                            val searchManga = it as NetWorkState<SearchMangaQuery.Page>
                            viewModel.hasNextPage = searchManga.data?.pageInfo?.hasNextPage ?: false
                            searchManga.data?.media?.forEach { manga ->
                                viewModel.searchList.add(SearchResult(mangaSearchResult = manga))
                            }
                        }
                        Constant.TARGET_CHARA -> {
                            val searchChara = it as NetWorkState<SearchCharacterQuery.Page>
                            viewModel.hasNextPage = searchChara.data?.pageInfo?.hasNextPage ?: false
                            searchChara.data?.characters?.forEach { chara ->
                                viewModel.searchList.add(SearchResult(charactersSearchResult = chara))
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    isLoading = false
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        })
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

        if (activity is SearchActivity) {
            (activity as SearchActivity).searchLiveData.observe(viewLifecycleOwner, { search ->
                viewModel.searchKeyword = search
                viewModel.searchList.clear()

                isLoading = false
                viewModel.page = 1
                viewModel.hasNextPage = true

                adapter.notifyDataSetChanged()

                if (search.isNotBlank()) {
                    viewModel.search(viewModel.searchKeyword)
                }
            })
        }
    }

    private fun initRecyclerView() {
        adapter = when (viewModel.searchPage) {
            "Anime" -> {
                SearchAnimeAdapter(viewModel.searchList) { id, idMal ->
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
                        this.putExtra("type", Constant.TARGET_MEDIA)
                        this.putExtra("id", id)
                        this.putExtra("idMal", idMal)
                    }
                    startActivity(intent)
                }
            }
            "Manga" -> {
                SearchMangaAdapter(viewModel.searchList) { id, idMal ->
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
                        this.putExtra("type", Constant.TARGET_MEDIA)
                        this.putExtra("id", id)
                        this.putExtra("idMal", idMal)
                    }
                    startActivity(intent)
                }
            }
            "Character" -> {
                SearchCharacterAdapter(viewModel.searchList) { id ->
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
                        this.putExtra("type", Constant.TARGET_CHARA)
                        this.putExtra("id", id)
                    }
                    startActivity(intent)
                }
            }
            else -> {
                SearchAnimeAdapter(viewModel.searchList) { id, idMal ->
                    val intent = Intent(this.context, BrowseActivity::class.java).apply {
                        this.putExtra("type", Constant.TARGET_MEDIA)
                        this.putExtra("id", id)
                        this.putExtra("idMal", idMal)
                    }
                    startActivity(intent)
                }
            }
        }
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(this.context)
    }

    private fun loadMore() {
        if (viewModel.hasNextPage) {
            viewModel.searchList.add(null)
            viewModel.search(viewModel.searchKeyword)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}