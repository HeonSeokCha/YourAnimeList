package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.network.response.SearchResult
import com.chs.youranimelist.network.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    var page: Int = 1
    var hasNextPage: Boolean = true
    var searchKeyword: String = ""
    var searchList: ArrayList<SearchResult?> = ArrayList()
    var searchPage: String = ""

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
                "Anime" -> repository.searchAnime(page, query)
                "Manga" -> repository.searchManga(page, query)
                "Character" -> repository.searchCharacter(page, query)
            }
        }
    }

    fun getObserver(): LiveData<*>? {
        return when (searchPage) {
            "Anime" -> searchAnimeResponse
            "Manga" -> searchMangaResponse
            "Character" -> searchCharaResponse
            else -> null
        }
    }
}