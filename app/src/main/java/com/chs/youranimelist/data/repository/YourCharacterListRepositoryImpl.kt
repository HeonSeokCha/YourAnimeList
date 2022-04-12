package com.chs.youranimelist.data.repository

import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.data.datasource.CharaListDao
import com.chs.youranimelist.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class YourCharacterListRepositoryImpl(
    private val dao: CharaListDao
) : YourCharacterListRepository {
    override fun getAllCharaList(): Flow<List<Character>> {
        return dao.getAllCharaList().flowOn(Dispatchers.IO)
    }

    override fun checkCharaList(charaId: Int): Flow<Character?> {
        return dao.checkCharaList(charaId)
    }

    override fun searchCharaList(charaName: String): Flow<List<Character>> {
        return dao.searchCharaList(charaName).flowOn(Dispatchers.IO)
    }

    override suspend fun insertCharaList(character: Character): Long {
        return dao.insertCharaList(character)
    }

    override suspend fun deleteCharaList(character: Character) {
        return dao.deleteCharaList(character)
    }
}