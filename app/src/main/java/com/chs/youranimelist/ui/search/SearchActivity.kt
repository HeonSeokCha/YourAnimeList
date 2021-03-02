package com.chs.youranimelist.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.databinding.ActivitySearchBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.type.MediaType
import com.chs.youranimelist.ui.browse.BrowseActivity
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var timerTask: TimerTask
    private val repository by lazy { SearchRepository() }
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = SearchViewModel(repository)
        setContentView(binding.root)
        initRecyclerView()
        initView()
    }

    private fun initView() {
//        binding.textInput.doAfterTextChanged {
//            if (it!!.length > 2) {
//                timerTask = Timer().schedule(500) {
//                    Handler(Looper.getMainLooper()).post {
//                        initObserver(it.toString())
//                    }
//                }
//            } else if (it!!.isEmpty()) {
//                searchAdapter.submitList(mutableListOf())
//            }
//        }
//        binding.textInput.doOnTextChanged { _, _, _, _ ->
//            if (::timerTask.isInitialized) {
//                timerTask.cancel()
//            }
//        }
        binding.textInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                initObserver(textView.text.toString())
                closeKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun initObserver(searchKeyword: String) {
        viewModel.getMediaSearch(
            searchKeyword = searchKeyword,
            searchType = MediaType.ANIME
        ).observe(this@SearchActivity, {
            searchAdapter.submitList(it.data)
        })
    }

    private fun initRecyclerView() {
        binding.rvSearch.apply {
            searchAdapter = SearchAdapter { id ->
                val intent = Intent(this@SearchActivity, BrowseActivity::class.java).apply {
                    this.putExtra("type", "ANIME")
                    this.putExtra("id", id)
                }
                startActivity(intent)
            }
            searchAdapter.setHasStableIds(true)
            this.layoutManager = LinearLayoutManager(this@SearchActivity)
            this.adapter = searchAdapter
        }
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