package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchAnime(
        page: Input<Int>,
        search: Input<String>
    ): Response<SearchAnimeQuery.Data>

    suspend fun searchManga(
        page: Input<Int>,
        search: Input<String>
    ): Response<SearchMangaQuery.Data>

    suspend fun searchCharacter(
        page: Input<Int>,
        search: Input<String>
    ): Response<SearchCharacterQuery.Data>

}