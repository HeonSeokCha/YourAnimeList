package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery

interface SearchRepository {

    suspend fun searchAnime(
        page: Int,
        search: String
    ): ApolloResponse<SearchAnimeQuery.Data>

    suspend fun searchManga(
        page: Int,
        search: String
    ): ApolloResponse<SearchMangaQuery.Data>

    suspend fun searchCharacter(
        page: Int,
        search: String
    ): ApolloResponse<SearchCharacterQuery.Data>

}