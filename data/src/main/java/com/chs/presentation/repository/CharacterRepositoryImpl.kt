package com.chs.presentation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.CharacterDetailQuery
import com.chs.SearchCharacterQuery
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import com.chs.presentation.mapper.toCharacterDetailInfo
import com.chs.presentation.mapper.toCharacterEntity
import com.chs.presentation.mapper.toCharacterInfo
import com.chs.presentation.paging.SearchCharacterPagingSource
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
            .data
            ?.character
            ?.toCharacterDetailInfo()!!
    }

    override suspend fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchCharacterPagingSource(
                apolloClient,
                search = name
            )
        }.flow
    }

    override fun getSavedCharacterList(): Flow<List<CharacterInfo>> {
        return dao.getAllCharaList().map {
            it.map { characterEntity ->
                characterEntity.toCharacterInfo()
            }
        }
    }

    override fun getSavedCharacterInfo(characterId: Int): Flow<CharacterInfo?> {
        return dao.checkCharaList(characterId).map {
            it.toCharacterInfo()
        }
    }

    override suspend fun insertCharacterInfo(characterInfo: CharacterInfo) {
        dao.insert(characterInfo.toCharacterEntity())
    }

    override suspend fun deleteCharacterInfo(characterInfo: CharacterInfo) {
        dao.delete(characterInfo.toCharacterEntity())
    }
}