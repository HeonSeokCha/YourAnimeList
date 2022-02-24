package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.toInput
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.dto.SearchResult
import com.chs.youranimelist.data.remote.repository.SearchRepository
import com.chs.youranimelist.data.remote.usecase.SearchAnimeUseCase
import com.chs.youranimelist.data.remote.usecase.SearchCharaUseCase
import com.chs.youranimelist.data.remote.usecase.SearchMangaUseCase
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
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
    var searchList: ArrayList<SearchResult?> = ArrayList()

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
        searchList.add(null)
    }
}