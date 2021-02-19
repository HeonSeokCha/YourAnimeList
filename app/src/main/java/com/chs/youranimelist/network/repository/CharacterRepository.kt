package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.network.ApolloServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository {
    @ExperimentalCoroutinesApi
    fun getCharacterInfo(charaId: Input<Int>): Flow<CharacterQuery.Data> {
        return ApolloServices.apolloClient.query(CharacterQuery(charaId)).toFlow().map {
            it.data!!
        }
    }
}