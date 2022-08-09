package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterDetail(charaId: Int): Flow<Resource<CharacterQuery.Data>>

    fun checkCharaList(charaId: Int): Flow<CharacterDto>

    fun getYourCharaList(): Flow<List<CharacterDto>>

    suspend fun insertCharacter(character: CharacterDto)

    suspend fun deleteCharacter(character: CharacterDto)

    fun searchCharaList(charaName: String): Flow<List<CharacterDto>>
}