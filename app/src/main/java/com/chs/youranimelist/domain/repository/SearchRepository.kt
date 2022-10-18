package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchAnime(search: String): Flow<PagingData<SearchAnimeQuery.Medium>>

    fun searchManga(search: String): Flow<PagingData<SearchMangaQuery.Medium>>

    fun searchCharacter(search: String): Flow<PagingData<SearchCharacterQuery.Character>>
}