package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.ListInfo

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo

    suspend fun getCharacterSearchResult(name: String): ListInfo<CharacterInfo>

    fun getSavedCharacterList(): List<CharacterInfo>

    fun getSavedCharacterInfo(): CharacterInfo?

    suspend fun insertCharacterInfo(characterInfo: CharacterInfo)

    suspend fun deleteCharacterInfo()

}