package com.chs.youranimelist.network.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.util.SingleLiveEvent
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.network.NetWorkState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

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