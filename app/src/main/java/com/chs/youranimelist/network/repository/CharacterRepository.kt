package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.SingleLiveEvent
import com.chs.youranimelist.network.ApolloServices
import com.chs.youranimelist.network.NetWorkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class CharacterRepository {

    private val _characterDetailResponse = SingleLiveEvent<NetWorkState<CharacterQuery.Data>>()
    val characterDetailResponse: LiveData<NetWorkState<CharacterQuery.Data>>
        get() = _characterDetailResponse

    suspend fun getCharacterDetail(charaId: Input<Int>) {
        _characterDetailResponse.postValue(NetWorkState.Loading())
        ApolloServices.apolloClient.query(CharacterQuery(charaId)).toFlow()
            .catch { e -> _characterDetailResponse.postValue(NetWorkState.Error(e.message.toString())) }
            .collect { _characterDetailResponse.postValue(NetWorkState.Success(it.data!!)) }
    }
}