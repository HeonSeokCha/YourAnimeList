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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchAnimeResponse =
        MutableStateFlow<NetWorkState<SearchAnimeQuery.Page>>(NetWorkState.Loading())
    private val searchAnimeResponse: StateFlow<NetWorkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse =
        MutableStateFlow<NetWorkState<SearchMangaQuery.Page>>(NetWorkState.Loading())
    private val searchMangaResponse: StateFlow<NetWorkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse =
        MutableStateFlow<NetWorkState<SearchCharacterQuery.Page>>(NetWorkState.Loading())
    private val searchCharaResponse: StateFlow<NetWorkState<SearchCharacterQuery.Page>>
        get() = _searchCharaResponse

    private val repository by lazy { SearchRepository() }

    private val _searchKeywordLiveData = SingleLiveEvent<String>()
    val searchKeywordLiveData: LiveData<String>
        get() = _searchKeywordLiveData


    var page: Int = 1
    var hasNextPage: Boolean = true

    var searchList: ArrayList<SearchResult?> = ArrayList()
    lateinit var searchPage: String

    fun setSearchKeyword(keyword: String) {
        _searchKeywordLiveData.value = keyword
    }

    fun search(query: String) {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    repository.searchAnime(page, query).catch { e ->
                        _searchAnimeResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchAnimeResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_MANGA -> {
                    repository.searchManga(page, query).catch { e ->
                        _searchMangaResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchMangaResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
                Constant.TARGET_CHARA -> {
                    repository.searchCharacter(page, query).catch { e ->
                        _searchCharaResponse.value = NetWorkState.Error(e.message.toString())
                    }.collect {
                        _searchCharaResponse.value = NetWorkState.Success(it.data?.page!!)
                    }
                }
            }
        }
    }

    fun getObserver(): StateFlow<*>? {
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