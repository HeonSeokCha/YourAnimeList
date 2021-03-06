package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class StudioRepository {

    private val _studioResponse = SingleLiveEvent<NetWorkState<StudioAnimeQuery.Studio>>()
    val studioResponse: LiveData<NetWorkState<StudioAnimeQuery.Studio>>
        get() = _studioResponse


    suspend fun getStudioAnime(studioId: Int, sort: MediaSort, page: Int) {
        ApolloServices.apolloClient.query(
            StudioAnimeQuery(
                studioId.toInput(),
                sort.toInput(),
                page.toInput()
            )
        ).toFlow()
            .catch { e ->
                _studioResponse.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                if (it.data?.studio == null) {
                    _studioResponse.postValue(NetWorkState.Error("Not Found"))
                } else {
                    _studioResponse.postValue(NetWorkState.Success(it.data!!.studio!!))
                }
            }
    }
}