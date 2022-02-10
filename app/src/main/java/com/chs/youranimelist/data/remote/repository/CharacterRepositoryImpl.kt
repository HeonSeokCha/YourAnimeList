package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.character.CharacterQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CharacterRepository {

    override suspend fun getCharacterDetail(charaId: Input<Int>) =
        apolloClient.query(CharacterQuery(charaId)).await()

}