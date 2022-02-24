package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : SearchRepository {

    override suspend fun searchAnime(
        page: Int,
        search: String
    ) = apolloClient.query(
        SearchAnimeQuery(page, search)
    ).execute()

    override suspend fun searchManga(
        page: Int,
        search: String
    ) = apolloClient.query(
        SearchMangaQuery(page, search)
    ).execute()

    override suspend fun searchCharacter(
        page: Int,
        search: String
    ) = apolloClient.query(
        SearchCharacterQuery(page, search)
    ).execute()

}