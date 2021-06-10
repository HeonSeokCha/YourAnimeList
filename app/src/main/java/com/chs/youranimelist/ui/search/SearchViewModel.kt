package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.network.response.SearchResult
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.util.Constant
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    var page: Int = 1
    var hasNextPage: Boolean = true
    var searchKeyword: String = ""
    var searchList: ArrayList<SearchResult?> = ArrayList()
    lateinit var searchPage: String

    private val searchAnimeResponse by lazy {
        repository.searchAnimeResponse
    }

    private val searchMangaResponse by lazy {
        repository.searchMangaResponse
    }

    private val searchCharaResponse by lazy {
        repository.searchCharaResponse
    }

    fun search(query: String) {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> repository.searchAnime(page, query)
                Constant.TARGET_MANGA -> repository.searchManga(page, query)
                Constant.TARGET_CHARA -> repository.searchCharacter(page, query)
            }
        }
    }

    fun getObserver(): LiveData<*>? {
        return when (searchPage) {
            Constant.TARGET_ANIME -> searchAnimeResponse
            Constant.TARGET_MANGA -> searchMangaResponse
            Constant.TARGET_CHARA -> searchCharaResponse
            else -> null
        }
    }

    fun loading() {
        searchList.add(null)
    }
}