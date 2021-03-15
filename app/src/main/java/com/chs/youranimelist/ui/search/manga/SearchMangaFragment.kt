package com.chs.youranimelist.ui.search.manga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.databinding.FragmentSearchMangaBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.search.SearchActivity
import com.chs.youranimelist.ui.search.SearchViewModel

class SearchMangaFragment : Fragment() {

    private var _binding: FragmentSearchMangaBinding? = null
    private lateinit var viewModel: SearchViewModel
    private val repository by lazy { SearchRepository() }
    private val binding get() = _binding!!
    private lateinit var searchMangaAdapter: SearchMangaAdapter

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
                initObserver(it)
            })
        }
    }

    private fun initObserver(searchKeyword: String) {
        viewModel.getMangaSearch(searchKeyword = searchKeyword)
        viewModel.mangaSearchUiState.asLiveData().observe(viewLifecycleOwner, {
            searchMangaAdapter.submitList(it.data)
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
            searchMangaAdapter.setHasStableIds(true)
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = searchMangaAdapter
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