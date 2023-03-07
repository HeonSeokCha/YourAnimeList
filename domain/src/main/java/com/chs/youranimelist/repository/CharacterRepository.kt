package com.chs.youranimelist.repository

import com.chs.youranimelist.model.CharacterDetailInfo
import com.chs.youranimelist.model.CharacterInfo
import com.chs.youranimelist.model.ListInfo

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): com.chs.youranimelist.model.CharacterDetailInfo

    suspend fun getCharacterSearchResult(name: String): com.chs.youranimelist.model.ListInfo<com.chs.youranimelist.model.CharacterInfo>

    fun getSavedCharacterList(): List<com.chs.youranimelist.model.CharacterInfo>

    fun getSavedCharacterInfo(): com.chs.youranimelist.model.CharacterInfo?

    suspend fun insertCharacterInfo(characterInfo: com.chs.youranimelist.model.CharacterInfo)

    suspend fun deleteCharacterInfo()

}