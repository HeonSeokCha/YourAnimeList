package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.ListInfo

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): CharacterDetailInfo

    suspend fun getCharacterSearchResult(name: String): ListInfo<CharacterInfo>

    fun getSavedCharacterList()

    fun getSavedCharacterInfo()

    suspend fun insertCharacterInfo()

    suspend fun deleteCharacterInfo()

}