package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getAnimeSearchResult(query: String): Flow<PagingData<AnimeInfo>>

    fun getCharacterSearchResult(name: String): Flow<PagingData<CharacterInfo>>

    suspend fun getSearchHistory(): List<String>

    suspend fun insertSearchHistory(title: String)

    suspend fun deleteSearchHistory(title: String)
}