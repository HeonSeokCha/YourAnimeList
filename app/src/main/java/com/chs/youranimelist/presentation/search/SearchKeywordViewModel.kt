package com.chs.youranimelist.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchKeywordViewModel : ViewModel() {
    private val _searchKeyword = MutableLiveData("")
    val searchKeyword: LiveData<String> get() = _searchKeyword

    fun onKeyWordChanged(keyword: String) {
        _searchKeyword.value = keyword
    }
}