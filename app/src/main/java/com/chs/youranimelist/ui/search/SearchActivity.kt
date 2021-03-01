package com.chs.youranimelist.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chs.youranimelist.databinding.ActivitySearchBinding
import com.chs.youranimelist.network.repository.SearchRepository

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private val repository by lazy { SearchRepository() }
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = SearchViewModel(repository)
        setContentView(binding.root)
    }

    private fun initObserver() {

    }

    private fun initRecyclerView() {

    }
}