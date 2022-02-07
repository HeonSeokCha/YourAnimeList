package com.chs.youranimelist.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.dto.SearchResult
import com.chs.youranimelist.data.remote.repository.SearchRepository
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
    private val repositoryImpl: SearchRepository
) : ViewModel() {


    private val _searchAnimeResponse = SingleLiveEvent<NetWorkState<SearchAnimeQuery.Page>>()
    private val searchAnimeResponse: LiveData<NetWorkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse = SingleLiveEvent<NetWorkState<SearchMangaQuery.Page>>()
    private val searchMangaResponse: LiveData<NetWorkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse = SingleLiveEvent<NetWorkState<SearchCharacterQuery.Page>>()
    private val searchCharaResponse: LiveData<NetWorkState<SearchCharacterQuery.Page>>
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
                    _searchAnimeResponse.postValue(NetWorkState.Loading())
                    repositoryImpl.searchAnime(page, query).catch { e ->
                        _searchAnimeResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _searchAnimeResponse.postValue(NetWorkState.Success(it.data?.page!!))
                    }
                }
                Constant.TARGET_MANGA -> {
                    _searchMangaResponse.postValue(NetWorkState.Loading())
                    repositoryImpl.searchManga(page, query).catch { e ->
                        _searchMangaResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _searchMangaResponse.postValue(NetWorkState.Success(it.data?.page!!))
                    }
                }
                Constant.TARGET_CHARA -> {
                    _searchCharaResponse.postValue(NetWorkState.Loading())
                    repositoryImpl.searchCharacter(page, query).catch { e ->
                        _searchCharaResponse.postValue(NetWorkState.Error(e.message.toString()))
                    }.collect {
                        _searchCharaResponse.postValue(NetWorkState.Success(it.data?.page!!))
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