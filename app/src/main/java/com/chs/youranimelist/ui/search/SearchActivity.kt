package com.chs.youranimelist.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.databinding.ActivitySearchBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.type.MediaType
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.browse.anime.AnimeDetailViewPagerAdapter
import com.chs.youranimelist.ui.search.anime.SearchAnimeAdapter
import com.chs.youranimelist.ui.search.anime.SearchAnimeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchAnimeViewModel
    private lateinit var searchAdapter: SearchAnimeAdapter
    private lateinit var timerTask: TimerTask
    private val repository by lazy { SearchRepository() }
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = SearchAnimeViewModel(repository)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.textSearchInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closeKeyboard()
                initTabView(textView.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun initTabView(searchKeyword: String) {
        binding.viewPagerSearch.adapter =
            SearchViewPagerAdapter(this, searchKeyword)
        binding.viewPagerSearch.isUserInputEnabled = false
        TabLayoutMediator(binding.searchTabLayout, binding.viewPagerSearch) { tab, position ->
            var tabArr: List<String> = listOf("Anime", "Manga", "Character")
            for (i in 0..position) {
                tab.text = tabArr[i]
            }
        }.attach()
    }

    private fun closeKeyboard() {
        if (this.currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                this.currentFocus!!.windowToken, 0
            )
        }
    }
}