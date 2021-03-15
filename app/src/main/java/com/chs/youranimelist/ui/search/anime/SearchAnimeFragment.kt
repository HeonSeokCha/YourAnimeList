package com.chs.youranimelist.ui.search.anime

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.databinding.FragmentSearchAnimeBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity
import com.chs.youranimelist.ui.search.SearchViewModel

class SearchAnimeFragment : Fragment() {
    private var _binding: FragmentSearchAnimeBinding? = null
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAnimeAdapter: SearchAnimeAdapter
    private val repository by lazy { SearchRepository() }
    private val binding get() = _binding!!

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
                initObserver(it)
            })
        }
    }

    private fun initObserver(searchKeyword: String) {
        viewModel.getAnimeSearch(searchKeyword = searchKeyword)
        viewModel.animeSearchUiState.asLiveData().observe(viewLifecycleOwner, {
            searchAnimeAdapter.submitList(it.data)
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
            searchAnimeAdapter.setHasStableIds(true)
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = searchAnimeAdapter
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