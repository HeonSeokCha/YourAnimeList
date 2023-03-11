package com.chs.presentation.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.CharacterQuery
import com.chs.presentation.source.KtorJikanService
import com.chs.presentation.source.db.dao.CharaListDao
import com.chs.presentation.domain.model.CharacterDetailInfo
import com.chs.presentation.domain.model.CharacterInfo
import com.chs.presentation.domain.model.ListInfo
import com.chs.presentation.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val dao: CharaListDao
) : CharacterRepository {
    override suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo {
        return apolloClient
            .query(CharacterQuery(Optional.present(characterId)))
            .execute()
            ?.data
            ?.character
    }

    override suspend fun getCharacterSearchResult(name: String): ListInfo<CharacterInfo> {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterList(): List<CharacterInfo> {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterInfo(): CharacterInfo? {
        TODO("Not yet implemented")
    }

    override suspend fun insertCharacterInfo(characterInfo: CharacterInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacterInfo() {
        TODO("Not yet implemented")
    }
}