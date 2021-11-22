package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.response.SearchResult
import com.chs.youranimelist.network.repository.SearchRepository
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchAnimeResponse = SingleLiveEvent<NetWorkState<SearchAnimeQuery.Page>>()
    private val searchAnimeResponse: LiveData<NetWorkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse = SingleLiveEvent<NetWorkState<SearchMangaQuery.Page>>()
    private val searchMangaResponse: LiveData<NetWorkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse = SingleLiveEvent<NetWorkState<SearchCharacterQuery.Page>>()
    private val searchCharaResponse: LiveData<NetWorkState<SearchCharacterQuery.Page>>
        get() = _searchCharaResponse

    private val repository by lazy { SearchRepository() }

    var page: Int = 1
    var hasNextPage: Boolean = true

    var searchList: ArrayList<SearchResult?> = ArrayList()
    var searchQuery: String = ""
    lateinit var searchPage: String

    fun search() {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    _searchAnimeResponse.value = NetWorkState.Loading()
                    repository.searchAnime(page, searchQuery).catch { e ->
                        _searchAnimeResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchAnimeResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_MANGA -> {
                    _searchMangaResponse.value = NetWorkState.Loading()
                    repository.searchManga(page, searchQuery).catch { e ->
                        _searchMangaResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchMangaResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_CHARA -> {
                    _searchCharaResponse.value = NetWorkState.Loading()
                    repository.searchCharacter(page, searchQuery).catch { e ->
                        _searchCharaResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchCharaResponse.value = NetWorkState.Success(it.data?.page!!)
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