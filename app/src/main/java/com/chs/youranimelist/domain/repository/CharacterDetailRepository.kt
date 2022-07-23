package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.CharacterQuery

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(charaId: Int): ApolloResponse<CharacterQuery.Data>
}