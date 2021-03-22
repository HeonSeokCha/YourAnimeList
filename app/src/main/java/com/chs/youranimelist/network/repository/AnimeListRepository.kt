package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.SingleLiveEvent
import com.chs.youranimelist.network.ApolloServices
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class AnimeListRepository {

    private val _animeListResponse = SingleLiveEvent<NetWorkState<AnimeListQuery.Data>>()
    val animeListResponse: LiveData<NetWorkState<AnimeListQuery.Data>>
        get() = _animeListResponse


    suspend fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>
    ) {
        _animeListResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(
            AnimeListQuery(page, sort, season, seasonYear)
        ).toFlow().catch { e ->
            _animeListResponse.postValue(NetWorkState.Error(e.message.toString()))
        }.collect { _animeListResponse.postValue(NetWorkState.Success(it.data!!)) }
    }
}