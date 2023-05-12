package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.CharacterDetailQuery
import com.chs.data.mapper.toCharacterDetailInfo
import com.chs.data.mapper.toCharacterEntity
import com.chs.data.mapper.toCharacterInfo
import com.chs.data.paging.CharaAnimePagingSource
import com.chs.data.paging.SearchCharacterPagingSource
import com.chs.data.source.db.dao.CharaListDao
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import com.chs.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
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

    override fun getCharacterDetailAnimeList(
        characterId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            CharaAnimePagingSource(
                apolloClient,
                charaId = characterId,
                sort = MediaSort.valueOf(sort)
            )
        }.flow
    }

    override fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>> {
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
            it?.toCharacterInfo()
        }
    }

    override fun getSavedSearchCharacterList(query: String): Flow<List<CharacterInfo>> {
        return dao.searchCharaList(query).map {
            it.map { characterEntity ->
                characterEntity.toCharacterInfo()
            }
        }
    }

    override suspend fun insertCharacterInfo(characterInfo: CharacterInfo) {
        dao.insert(characterInfo.toCharacterEntity())
    }

    override suspend fun deleteCharacterInfo(characterInfo: CharacterInfo) {
        dao.delete(characterInfo.toCharacterEntity())
    }
}