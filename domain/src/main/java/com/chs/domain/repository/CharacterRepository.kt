package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharacterRepository : BaseMediaRepository<CharacterInfo> {

    suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo

    fun getCharacterDetailAnimeList(
        characterId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>>

    override suspend fun insertMediaInfo(info: CharacterInfo)

    override fun getSavedMediaInfoList(): Flow<List<CharacterInfo>>

    override suspend fun deleteMediaInfo(info: CharacterInfo)

    override fun getSavedMediaInfo(id: Int): Flow<CharacterInfo?>

}