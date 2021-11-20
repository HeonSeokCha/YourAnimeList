package com.chs.youranimelist.ui.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.chs.youranimelist.databinding.ActivitySearchBinding
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.util.Constant
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    val searchLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initClick()
        initTabView()
    }

    private fun initClick() {
        binding.searchBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.searchClear.setOnClickListener {
            binding.searchBarEditText.text.clear()
        }
    }

    private fun initView() {
        binding.searchBarEditText.requestFocus()
        binding.searchBarEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closeKeyboard()
                viewModel.setSearchKeyword(textView.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun initTabView() {
        val tabArr: List<String> = listOf(
            Constant.TARGET_ANIME, Constant.TARGET_MANGA, Constant.TARGET_CHARA
        )
        binding.viewPagerSearch.adapter = SearchViewPagerAdapter(this, tabArr)
        TabLayoutMediator(binding.searchTabLayout, binding.viewPagerSearch) { tab, position ->
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