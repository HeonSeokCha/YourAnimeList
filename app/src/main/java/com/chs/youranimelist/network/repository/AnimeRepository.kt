package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.*
import com.chs.youranimelist.network.ApolloServices
import com.chs.youranimelist.network.NetWorkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AnimeRepository {

    private val _homeRecommendResponse =
        SingleLiveEvent<NetWorkState<HomeRecommendListQuery.Data>>()
    val homeRecommendResponse: LiveData<NetWorkState<HomeRecommendListQuery.Data>>
        get() = _homeRecommendResponse

    private val _animeDetailResponse = SingleLiveEvent<NetWorkState<AnimeDetailQuery.Data>>()
    val animeDetailResponse: LiveData<NetWorkState<AnimeDetailQuery.Data>>
        get() = _animeDetailResponse

    private val _animeOverviewResponse = SingleLiveEvent<NetWorkState<AnimeOverviewQuery.Data>>()
    val animeOverviewResponse: LiveData<NetWorkState<AnimeOverviewQuery.Data>>
        get() = _animeOverviewResponse

    private val _animeCharacterResponse = SingleLiveEvent<NetWorkState<AnimeCharacterQuery.Data>>()
    val animeCharacterResponse: LiveData<NetWorkState<AnimeCharacterQuery.Data>>
        get() = _animeCharacterResponse

    private val _animeRecommendResponse = SingleLiveEvent<NetWorkState<AnimeRecommendQuery.Data>>()
    val animeRecommendResponse: LiveData<NetWorkState<AnimeRecommendQuery.Data>>
        get() = _animeRecommendResponse

    suspend fun getHomeRecList() {
        _homeRecommendResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(
            HomeRecommendListQuery(
                ConvertDate.getCurrentSeason().toInput(),
                ConvertDate.getNextSeason().toInput(),
                ConvertDate.getCurrentYear(false).toInput(),
                ConvertDate.getCurrentYear(true).toInput()
            )
        ).toFlow()
            .catch { e -> _homeRecommendResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _homeRecommendResponse.postValue(NetWorkState.Success(it.data!!)) }
    }

    suspend fun getAnimeDetail(animeId: Input<Int>) {
        _animeDetailResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(AnimeDetailQuery(animeId)).toFlow()
            .catch { e -> _animeDetailResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _animeDetailResponse.postValue(NetWorkState.Success(it.data!!)) }
    }

    suspend fun getAnimeOverview(animeId: Input<Int>) {
        _animeOverviewResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(AnimeOverviewQuery(animeId)).toFlow()
            .catch { e -> _animeOverviewResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _animeOverviewResponse.postValue(NetWorkState.Success(it.data!!)) }
    }

    suspend fun getAnimeCharacter(animeId: Input<Int>) {
        _animeCharacterResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(AnimeCharacterQuery(animeId)).toFlow()
            .catch { e -> _animeCharacterResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _animeCharacterResponse.postValue(NetWorkState.Success(it.data!!)) }
    }

    suspend fun getAnimeRecList(animeId: Input<Int>) {
        _animeRecommendResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(AnimeRecommendQuery(animeId)).toFlow()
            .catch { e -> _animeRecommendResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _animeRecommendResponse.postValue(NetWorkState.Success(it.data!!)) }
    }
}