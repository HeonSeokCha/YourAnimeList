package com.chs.youranimelist.data.repository

import com.chs.youranimelist.domain.repository.CharacterRepository

class CharacterRepositoryImpl : CharacterRepository {
    override suspend fun getCharacterDetailInfo(characterId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterSearchResult(name: String) {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterList() {
        TODO("Not yet implemented")
    }

    override fun getSavedCharacterInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun insertCharacterInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacterInfo() {
        TODO("Not yet implemented")
    }
}