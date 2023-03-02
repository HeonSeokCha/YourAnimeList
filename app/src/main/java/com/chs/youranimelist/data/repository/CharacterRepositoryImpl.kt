package com.chs.youranimelist.data.repository

import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.ListInfo
import com.chs.youranimelist.domain.repository.CharacterRepository

class CharacterRepositoryImpl : CharacterRepository {
    override suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterSearchResult(name: String): ListInfo<CharacterInfo> {
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