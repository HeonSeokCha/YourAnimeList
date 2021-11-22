package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chs.youranimelist.util.SingleLiveEvent

class SearchKeywordViewModel : ViewModel() {

    private val _searchKeywordLiveData = SingleLiveEvent<String>()
    val searchKeywordLiveData: LiveData<String>
        get() = _searchKeywordLiveData

    fun setSearchKeyword(keyWord: String) {
        _searchKeywordLiveData.value = keyWord
    }
}