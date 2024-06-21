package com.chs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.chs.data.CharacterDetailQuery
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.data.mapper.toCharacterDetailInfo
import com.chs.data.mapper.toCharacterEntity
import com.chs.data.mapper.toCharacterInfo
import com.chs.data.paging.CharaAnimePagingSource
import com.chs.data.source.db.dao.CharaListDao
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import com.chs.data.type.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val dao: CharaListDao
) : CharacterRepository {

    override fun getCharacterDetailInfo(characterId: Int): Flow<Resource<CharacterDetailInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apolloClient
                    .query(CharacterDetailQuery(Optional.present(characterId)))
                    .execute()

                if (response.hasErrors()) {
                    return@flow emit(Resource.Error(response.errors!!.first().message))
                }

                emit(Resource.Success(response.data?.character?.toCharacterDetailInfo()!!))
            } catch (e: Exception) {
                throw e
            }
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
                sort = MediaSort.valueOf(sort)
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