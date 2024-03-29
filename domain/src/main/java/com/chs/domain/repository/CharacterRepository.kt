package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo

    fun getCharacterDetailAnimeList(
        characterId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>>


    fun getSavedCharacterList(): Flow<List<CharacterInfo>>

    fun getSavedCharacterInfo(characterId: Int): Flow<CharacterInfo?>

    suspend fun insertCharacterInfo(characterInfo: CharacterInfo)

    suspend fun deleteCharacterInfo(characterInfo: CharacterInfo)

}