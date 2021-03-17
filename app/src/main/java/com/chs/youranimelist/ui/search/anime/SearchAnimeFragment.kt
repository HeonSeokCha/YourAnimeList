package com.chs.youranimelist.ui.search.anime

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.FragmentSearchAnimeBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity
import com.chs.youranimelist.ui.search.SearchViewModel

class SearchAnimeFragment : Fragment() {
    private var _binding: FragmentSearchAnimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private var isLoading: Boolean = false
    private var searchKeyWord: String = ""
    private val repository by lazy { SearchRepository() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAnimeBinding.inflate(inflater, container, false)
        viewModel = SearchViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        if (activity is SearchActivity) {
            (activity as SearchActivity).searchLiveData.observe(viewLifecycleOwner, {
                searchKeyWord = it
                viewModel.animeSearchList.clear()

                isLoading = false
                viewModel.animeSearchPage = 1
                viewModel.animeHasNextPage = true

                searchAnimeAdapter.notifyDataSetChanged()
                initObserver(searchKeyWord)
            })
        }
        initView()
    }

    private fun initObserver(searchKeyword: String) {
        viewModel.getAnimeSearch(searchKeyword = searchKeyword)
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

                    it.data!!.forEach { searchAnime ->
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
    }

    private fun initRecyclerView() {
        binding.rvSearchAnime.apply {
            searchAnimeAdapter = SearchAnimeAdapter { id ->
                val intent = Intent(this.context, BrowseActivity::class.java).apply {
                    this.putExtra("type", "Media")
                    this.putExtra("id", id)
                }
                startActivity(intent)
            }
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = searchAnimeAdapter
        }
    }

    private fun loadMore() {
        if (viewModel.animeHasNextPage) {
            viewModel.animeSearchList.add(null)
            searchAnimeAdapter.notifyItemInserted(viewModel.animeSearchList.lastIndex)
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