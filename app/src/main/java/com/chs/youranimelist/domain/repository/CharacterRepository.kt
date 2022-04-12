package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.browse.character.CharacterQuery
interface CharacterRepository {
    suspend fun getCharacterDetail(charaId: Int): ApolloResponse<CharacterQuery.Data>
}