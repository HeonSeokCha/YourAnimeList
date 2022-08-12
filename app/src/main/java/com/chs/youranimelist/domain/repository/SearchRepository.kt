package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchAnime(
        page: Int,
        search: String
    ): Flow<Resource<SearchAnimeQuery.Data>>

    suspend fun searchManga(
        page: Int,
        search: String
    ): Flow<Resource<SearchMangaQuery.Data>>

    suspend fun searchCharacter(
        page: Int,
        search: String
    ): Flow<Resource<SearchCharacterQuery.Data>>
}