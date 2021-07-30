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

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _searchAnimeResponse = SingleLiveEvent<NetWorkState<SearchAnimeQuery.Page>>()
    val searchAnimeResponse: LiveData<NetWorkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse = SingleLiveEvent<NetWorkState<SearchMangaQuery.Page>>()
    val searchMangaResponse: LiveData<NetWorkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse = SingleLiveEvent<NetWorkState<SearchCharacterQuery.Page>>()
    val searchCharaResponse: LiveData<NetWorkState<SearchCharacterQuery.Page>>
        get() = _searchCharaResponse

    var page: Int = 1
    var hasNextPage: Boolean = true
    var searchKeyword: String = ""
    var searchList: ArrayList<SearchResult?> = ArrayList()
    lateinit var searchPage: String

    fun search(query: String) {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    _searchAnimeResponse.value = NetWorkState.Loading()
                    repository.searchAnime(page, query).catch { e ->
                        _searchAnimeResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchAnimeResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_MANGA -> {
                    _searchMangaResponse.value = NetWorkState.Loading()
                    repository.searchManga(page, query).catch { e ->
                        _searchMangaResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchMangaResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_CHARA -> {
                    _searchCharaResponse.value = NetWorkState.Loading()
                    repository.searchCharacter(page, query).catch { e ->
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