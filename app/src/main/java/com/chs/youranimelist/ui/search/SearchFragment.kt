package com.chs.youranimelist.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentSearchBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.adapter.SearchAnimeAdapter
import com.chs.youranimelist.ui.search.adapter.SearchCharacterAdapter
import com.chs.youranimelist.ui.search.adapter.SearchMangaAdapter

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private lateinit var searchMangaAdapter: SearchMangaAdapter
    private lateinit var searchCharaAdapter: SearchCharacterAdapter
    private var isLoading: Boolean = false
    private var searchKeyWord: String = ""
    private var searchPage: String = ""
    private val repository by lazy { SearchRepository() }

    companion object {
        const val SEARCH_TYPE: String = "searchType"
    }

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
        searchPage = arguments?.getString(SEARCH_TYPE)!!
        initRecyclerView()
        initView()
    }

    private fun initObserver(searchKeyWord: String) {
        when (searchPage) {
            "Anime" -> {
                viewModel.getAnimeSearch(searchKeyWord)
                viewModel.animeSearchUiState.asLiveData().observe(viewLifecycleOwner, {
                    when (it.responseState) {
                        ResponseState.LOADING -> Unit
                        ResponseState.SUCCESS -> {
                            if (isLoading) {
                                viewModel.animeSearchList.removeAt(viewModel.animeSearchList.lastIndex)
                                searchAnimeAdapter.notifyItemRemoved(viewModel.animeSearchList.size)
                                isLoading = false
                            }
                            viewModel.animeSearchPage += 1
                            viewModel.animeHasNextPage =
                                it.data?.pageInfo?.hasNextPage ?: false

                            it.data?.media?.forEach { searchAnime ->
                                viewModel.animeSearchList.add(searchAnime)
                            }

                            searchAnimeAdapter.submitList(viewModel.animeSearchList)
                        }
                        ResponseState.ERROR -> {
                            Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                            isLoading = false
                        }
                    }
                })
            }
            "Manga" -> {
                viewModel.getMangaSearch(searchKeyWord)
                viewModel.mangaSearchUiState.asLiveData().observe(viewLifecycleOwner, {
                    when (it.responseState) {
                        ResponseState.LOADING -> Unit
                        ResponseState.SUCCESS -> {
                            if (isLoading) {
                                viewModel.mangaSearchList.removeAt(viewModel.mangaSearchList.lastIndex)
                                searchMangaAdapter.notifyItemRemoved(viewModel.mangaSearchList.size)
                                isLoading = false
                            }
                            viewModel.mangaHasNextPage =
                                it.data?.fragments?.pageInfo?.pageInfo?.hasNextPage ?: false
                            it.data!!.media!!.forEach { mangaList ->
                                viewModel.mangaSearchList.add(mangaList)
                            }
                            searchMangaAdapter.submitList(viewModel.mangaSearchList)
                        }
                        ResponseState.ERROR -> {
                            Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                            isLoading = false
                        }
                    }
                })
            }
            "Character" -> {
                viewModel.getCharacterSearch(searchKeyWord)
                viewModel.charaSearchUiState.asLiveData().observe(viewLifecycleOwner, {
                    when (it.responseState) {
                        ResponseState.LOADING -> Unit
                        ResponseState.SUCCESS -> {
                            if (isLoading) {
                                viewModel.charaSearchList.removeAt(viewModel.charaSearchList.lastIndex)
                                searchCharaAdapter.notifyItemRemoved(viewModel.charaSearchList.size)
                                isLoading = false
                            }
                            viewModel.charaHasNextPage =
                                it.data?.fragments?.pageInfo?.pageInfo?.hasNextPage ?: false
                            it.data!!.characters!!.forEach { character ->
                                viewModel.charaSearchList.add(character)
                            }
                            searchCharaAdapter.submitList(viewModel.charaSearchList)
                        }
                        ResponseState.ERROR -> {
                            Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                            isLoading = false
                        }
                    }
                })
            }
        }

    }

    private fun initView() {
        binding.rvSearchAnime.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            (activity as SearchActivity).searchLiveData.observe(viewLifecycleOwner, {
                searchKeyWord = it
                viewModel.animeSearchList.clear()
                initObserver(it)
                isLoading = false
                viewModel.animeSearchPage = 1
                viewModel.animeHasNextPage = true
            })
        }
    }

    private fun initRecyclerView() {
        binding.rvSearchAnime.apply {
            when (searchPage) {
                "Anime" -> {
                    searchAnimeAdapter = SearchAnimeAdapter { id ->
                        val intent = Intent(this.context, BrowseActivity::class.java).apply {
                            this.putExtra("type", "Media")
                            this.putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                    this.adapter = searchAnimeAdapter
                }
                "Manga" -> {
                    searchMangaAdapter = SearchMangaAdapter { id ->
                        val intent = Intent(this.context, BrowseActivity::class.java).apply {
                            this.putExtra("type", "Media")
                            this.putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                    this.adapter = searchMangaAdapter
                }
                "Character" -> {
                    searchCharaAdapter = SearchCharacterAdapter { id ->
                        val intent = Intent(this.context, BrowseActivity::class.java).apply {
                            this.putExtra("type", "CHARA")
                            this.putExtra("id", id)
                        }
                        startActivity(intent)
                    }
                    this.adapter = searchCharaAdapter
                }
            }

            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    private fun loadMore() {
        if (viewModel.animeHasNextPage) {
            viewModel.animeSearchList.add(null)
            searchAnimeAdapter.notifyItemInserted(viewModel.animeSearchList.lastIndex)
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