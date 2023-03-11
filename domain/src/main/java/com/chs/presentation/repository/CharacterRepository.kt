package com.chs.presentation.repository

interface CharacterRepository {

    suspend fun getCharacterDetailInfo(characterId: Int): com.chs.presentation.model.CharacterDetailInfo

    suspend fun getCharacterSearchResult(name: String): com.chs.presentation.model.ListInfo<com.chs.presentation.model.CharacterInfo>

    fun getSavedCharacterList(): List<com.chs.presentation.model.CharacterInfo>

    fun getSavedCharacterInfo(): com.chs.presentation.model.CharacterInfo?

    suspend fun insertCharacterInfo(characterInfo: com.chs.presentation.model.CharacterInfo)

    suspend fun deleteCharacterInfo()

}