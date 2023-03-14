package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo

    suspend fun getCharacterSearchResult(name: String): PagingData<CharacterInfo>

    fun getSavedCharacterList(): Flow<List<CharacterInfo>>

    fun getSavedCharacterInfo(): Flow<CharacterInfo>?

    suspend fun insertCharacterInfo(characterInfo: CharacterInfo)

    suspend fun deleteCharacterInfo(characterInfo: CharacterInfo)

}