package com.chs.youranimelist.domain.repository

import app.cash.paging.PagingData
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.youranimelist.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharacterRepository : BaseMediaRepository<CharacterInfo> {

    suspend fun getCharacterDetailInfo(characterId: Int): DataResult<CharacterDetailInfo, DataError.RemoteError>

    fun getCharacterDetailAnimeList(
        characterId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>>

    override suspend fun insertMediaInfo(info: CharacterInfo)

    override fun getSavedMediaInfoList(): Flow<List<CharacterInfo>>

    override suspend fun deleteMediaInfo(info: CharacterInfo)

    override fun getSavedMediaInfo(id: Int): Flow<CharacterInfo?>

}