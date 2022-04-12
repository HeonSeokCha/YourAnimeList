package com.chs.youranimelist.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.usecase.SearchAnimeUseCase
import com.chs.youranimelist.domain.usecase.SearchCharaUseCase
import com.chs.youranimelist.domain.usecase.SearchMangaUseCase
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
    private val searchCharaUseCase: SearchCharaUseCase
) : ViewModel() {


    private val _searchAnimeResponse = SingleLiveEvent<NetworkState<SearchAnimeQuery.Page>>()
    private val searchAnimeResponse: LiveData<NetworkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse = SingleLiveEvent<NetworkState<SearchMangaQuery.Page>>()
    private val searchMangaResponse: LiveData<NetworkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse = SingleLiveEvent<NetworkState<SearchCharacterQuery.Page>>()
    private val searchCharaResponse: LiveData<NetworkState<SearchCharacterQuery.Page>>
        get() = _searchCharaResponse


    lateinit var searchPage: String
    var searchKeyword: String = ""
    var page: Int = 1
    var hasNextPage: Boolean = true
    var searchAnimeList: ArrayList<SearchAnimeQuery.Medium?> = ArrayList()
    var searchMangaList: ArrayList<SearchMangaQuery.Medium?> = ArrayList()
    var searchCharaList: ArrayList<SearchCharacterQuery.Character?> = ArrayList()

    fun search(query: String) {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    searchAnimeUseCase(page, query).collect {
                        _searchAnimeResponse.value = it
                    }
                }
                Constant.TARGET_MANGA -> {
                    searchMangaUseCase(page, query).collect {
                        _searchMangaResponse.value = it
                    }
                }
                Constant.TARGET_CHARA -> {
                    searchCharaUseCase(page, query).collect {
                        _searchCharaResponse.value = it
                    }
                }
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
        when (searchPage) {
            Constant.TARGET_ANIME -> searchAnimeList.add(null)
            Constant.TARGET_MANGA -> searchMangaList.add(null)
            Constant.TARGET_CHARA -> searchCharaList.add(null)
        }
    }

    fun isSearchEmpty(): Boolean {
        return when (searchPage) {
            Constant.TARGET_ANIME -> searchAnimeList.isEmpty()
            Constant.TARGET_MANGA -> searchMangaList.isEmpty()
            Constant.TARGET_CHARA -> searchCharaList.isEmpty()
            else -> true
        }
    }

    fun finishLoading() {
        when (searchPage) {
            Constant.TARGET_ANIME -> searchAnimeList.removeAt(searchAnimeList.lastIndex)
            Constant.TARGET_MANGA -> searchMangaList.removeAt(searchMangaList.lastIndex)
            Constant.TARGET_CHARA -> searchCharaList.removeAt(searchCharaList.lastIndex)
        }
    }

    fun clear() {
        when (searchPage) {
            Constant.TARGET_ANIME -> searchAnimeList.clear()
            Constant.TARGET_MANGA -> searchMangaList.clear()
            Constant.TARGET_CHARA -> searchCharaList.clear()
        }
    }
}