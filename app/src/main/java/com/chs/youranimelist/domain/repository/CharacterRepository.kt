package com.chs.youranimelist.domain.repository

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int)

    suspend fun getCharacterSearchResult(name: String)

    fun getSavedCharacterList()

    fun getSavedCharacterInfo()

    suspend fun insertCharacterInfo()

    suspend fun deleteCharacterInfo()

}