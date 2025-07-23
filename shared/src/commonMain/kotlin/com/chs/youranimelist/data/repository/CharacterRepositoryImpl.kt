package com.chs.youranimelist.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.chs.youranimelist.data.CharacterDetailQuery
import com.chs.common.Constants
import com.chs.youranimelist.data.mapper.toCharacterDetailInfo
import com.chs.youranimelist.data.mapper.toCharacterEntity
import com.chs.youranimelist.data.mapper.toCharacterInfo
import com.chs.youranimelist.data.paging.CharaAnimePagingSource
import com.chs.youranimelist.data.source.db.dao.CharaListDao
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.data.type.MediaSort
import com.chs.common.DataError
import com.chs.common.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dao: CharaListDao
) : CharacterRepository {

    override suspend fun getCharacterDetailInfo(characterId: Int): DataResult<CharacterDetailInfo, DataError.RemoteError> {
        return try {
            val response = apolloClient
                .query(CharacterDetailQuery(Optional.present(characterId)))
                .execute()

            if (response.data == null) {
                return if (response.exception == null) {
                    DataResult.Error(DataError.RemoteError(response.errors!!.first().message))
                } else {
                    DataResult.Error(DataError.RemoteError(response.exception!!.message))
                }
            }
            DataResult.Success(response.data?.character?.toCharacterDetailInfo()!!)
        } catch (e: Exception) {
            DataResult.Error(DataError.RemoteError(e.message))
        }
    }

    override fun getCharacterDetailAnimeList(
        characterId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGING_SIZE,
                initialLoadSize = Constants.PAGING_SIZE * 3
            )
        ) {
            CharaAnimePagingSource(
                apolloClient,
                charaId = characterId,
                sort = MediaSort.safeValueOf(sort)
            )
        }.flow
    }

    override fun getSavedMediaInfoList(): Flow<List<CharacterInfo>> {
        return dao.getAllCharaList().map {
            it.map { characterEntity ->
                characterEntity.toCharacterInfo()
            }
        }
    }

    override fun getSavedMediaInfo(id: Int): Flow<CharacterInfo?> {
        return dao.checkCharaList(id).map {
            it?.toCharacterInfo()
        }
    }

    override suspend fun insertMediaInfo(info: CharacterInfo) {
        dao.insert(info.toCharacterEntity())
    }

    override suspend fun deleteMediaInfo(info: CharacterInfo) {
        dao.delete(info.toCharacterEntity())
    }
}