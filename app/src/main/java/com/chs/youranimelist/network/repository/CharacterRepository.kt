package com.chs.youranimelist.network.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.character.CharacterQuery
import javax.inject.Inject

class CharacterRepository(
    private val apolloClient: ApolloClient
) {

    fun getCharacterDetail(charaId: Input<Int>) =
        apolloClient.query(CharacterQuery(charaId)).toFlow()

}