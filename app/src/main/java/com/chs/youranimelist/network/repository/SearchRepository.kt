package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.util.SingleLiveEvent
import com.chs.youranimelist.network.ApolloServices
import com.chs.youranimelist.network.NetWorkState
import kotlinx.coroutines.flow.*

class SearchRepository {

    private val _searchAnimeResponse = SingleLiveEvent<NetWorkState<SearchAnimeQuery.Page>>()
    val searchAnimeResponse: LiveData<NetWorkState<SearchAnimeQuery.Page>>
        get() = _searchAnimeResponse

    private val _searchMangaResponse = SingleLiveEvent<NetWorkState<SearchMangaQuery.Page>>()
    val searchMangaResponse: LiveData<NetWorkState<SearchMangaQuery.Page>>
        get() = _searchMangaResponse

    private val _searchCharaResponse = SingleLiveEvent<NetWorkState<SearchCharacterQuery.Page>>()
    val searchCharaResponse: LiveData<NetWorkState<SearchCharacterQuery.Page>>
        get() = _searchCharaResponse


    suspend fun searchAnime(page: Int, search: String) {
        _searchAnimeResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(
            SearchAnimeQuery(page.toInput(), search.toInput())
        ).toFlow().catch { e ->
            _searchAnimeResponse.postValue(NetWorkState.Error(e.message.toString()))
        }.collect { _searchAnimeResponse.postValue(NetWorkState.Success(it.data?.page!!)) }
    }

    suspend fun searchManga(page: Int, search: String) {
        _searchMangaResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(
            SearchMangaQuery(page.toInput(), search.toInput())
        ).toFlow().catch { e ->
            _searchMangaResponse.postValue(NetWorkState.Error(e.message.toString()))
        }.collect { _searchMangaResponse.postValue(NetWorkState.Success(it.data?.page!!)) }
    }

    suspend fun searchCharacter(page: Int, search: String) {
        _searchCharaResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(
            SearchCharacterQuery(page.toInput(), search.toInput())
        ).toFlow().catch { e ->
            _searchCharaResponse.postValue(NetWorkState.Error(e.message.toString()))
        }.collect { _searchCharaResponse.postValue(NetWorkState.Success(it.data?.page!!)) }
    }
}