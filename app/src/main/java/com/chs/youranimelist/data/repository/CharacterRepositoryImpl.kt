package com.chs.youranimelist.data.repository

import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CharacterRepository {

    override suspend fun getCharacterDetail(
        charaId: Int
    ) = apolloClient.query(CharacterQuery(charaId)).execute()

}