package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.network.services.ApolloServices

class CharacterRepository {

    fun getCharacterDetail(charaId: Input<Int>) =
        ApolloServices.apolloClient.query(CharacterQuery(charaId)).toFlow()

}