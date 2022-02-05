package com.chs.youranimelist.data.domain.repository

import com.chs.youranimelist.data.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface YourCharacterListRepository {

    fun getAllCharaList(): Flow<List<Character>>

    fun checkCharaList(charaId: Int): Flow<Character>

    fun searchCharaList(charaName: String): Flow<List<Character>>

    suspend fun insertCharaList(character: Character): Long

    suspend fun deleteCharaList(character: Character)

}