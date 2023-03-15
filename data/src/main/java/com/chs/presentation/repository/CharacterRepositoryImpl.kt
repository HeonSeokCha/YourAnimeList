package com.chs.presentation.repository

import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.CharacterDetailQuery
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import com.chs.presentation.source.KtorJikanService
import com.chs.presentation.source.db.dao.CharaListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val jikanService: KtorJikanService,
    private val dao: CharaListDao
) : CharacterRepository {
    override suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo {
        return apolloClient
            .query(CharacterDetailQuery(Optional.present(characterId)))
            .execute()
            ?.data
            ?.character
    }

    override suspend fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>> {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterList(): Flow<List<CharacterInfo>> {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterInfo(): Flow<CharacterInfo?> {
    }

    override suspend fun insertCharacterInfo(characterInfo: CharacterInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacterInfo(characterInfo: CharacterInfo) {
        TODO("Not yet implemented")
    }
}