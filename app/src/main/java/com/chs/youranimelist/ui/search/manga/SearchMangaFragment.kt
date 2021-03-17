package com.chs.youranimelist.ui.search.manga

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
import com.chs.youranimelist.databinding.FragmentSearchMangaBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity
import com.chs.youranimelist.ui.search.SearchViewModel

class SearchMangaFragment : Fragment() {

    private var _binding: FragmentSearchMangaBinding? = null
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchMangaAdapter: SearchMangaAdapter
    private val repository by lazy { SearchRepository() }
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var searchKeyWord: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMangaBinding.inflate(inflater, container, false)
        viewModel = SearchViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        if (activity is SearchActivity) {
            (activity as SearchActivity).searchLiveData.observe(viewLifecycleOwner, {
                searchKeyWord = it
                initObserver(searchKeyWord)
            })
        }
        initView()
    }

    private fun initObserver(searchKeyword: String) {
        Log.d("searchKeyword", searchKeyword)
        viewModel.getMangaSearch(searchKeyword = searchKeyword)
        viewModel.mangaSearchUiState.asLiveData().observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> Unit
                ResponseState.SUCCESS -> {
                    if (isLoading) {
                        viewModel.mangaSearchList.removeAt(viewModel.mangaSearchList.lastIndex)
                        searchMangaAdapter.notifyItemRemoved(viewModel.mangaSearchList.size)
                        isLoading = false
                    }
                    it.data!!.forEach { mangaList ->
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

    private fun initView() {
        binding.rvSearchManga.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun initRecyclerView() {
        binding.rvSearchManga.apply {
            searchMangaAdapter = SearchMangaAdapter { id ->
                val intent = Intent(this.context, BrowseActivity::class.java).apply {
                    this.putExtra("type", "Media")
                    this.putExtra("id", id)
                }
                startActivity(intent)
            }
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = searchMangaAdapter
        }
    }

    private fun loadMore() {
        if (viewModel.mangaHasNextPage) {
            viewModel.mangaSearchList.add(null)
            searchMangaAdapter.notifyItemInserted(viewModel.mangaSearchList.lastIndex)
            viewModel.mangaSearchPage += 1
            initObserver(searchKeyWord)
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