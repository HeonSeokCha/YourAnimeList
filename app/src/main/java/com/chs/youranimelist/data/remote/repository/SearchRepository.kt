package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Response
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchAnime(page: Int, search: String): Flow<Response<SearchAnimeQuery.Data>>

    fun searchManga(page: Int, search: String): Flow<Response<SearchMangaQuery.Data>>

    fun searchCharacter(page: Int, search: String): Flow<Response<SearchCharacterQuery.Data>>

}