package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>>

    fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>>

    fun getSearchHistory(): Flow<List<String>>

    suspend fun insertSearchHistory(title: String)

    suspend fun deleteSearchHistory(title: String)
}