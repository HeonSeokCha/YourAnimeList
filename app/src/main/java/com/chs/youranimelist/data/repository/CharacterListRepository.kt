package com.chs.youranimelist.data.repository

import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.YourListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class CharacterListRepository(private val dao: YourListDao) {

    fun getAllCharaList() = dao.getAllCharaList().flowOn(Dispatchers.IO)

    fun checkCharaList(charaId: Int) = dao.checkCharaList(charaId).flowOn(Dispatchers.IO)

    fun searchCharaList(charaName: String) = dao.searchCharaList(charaName).flowOn(Dispatchers.IO)

    suspend fun insertCharaList(character: Character) {
        dao.insertCharaList(character)
    }

    suspend fun deleteCharaList(character: Character) {
        dao.deleteCharaList(character)
    }
}