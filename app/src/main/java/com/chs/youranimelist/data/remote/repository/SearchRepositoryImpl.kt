package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toFlow
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
        page: Input<Int>,
        search: Input<String>
    ) = apolloClient.query(
        SearchAnimeQuery(page, search)
    ).await()

    override suspend fun searchManga(
        page: Input<Int>,
        search: Input<String>
    ) = apolloClient.query(
        SearchMangaQuery(page, search)
    ).await()

    override suspend fun searchCharacter(
        page: Input<Int>,
        search: Input<String>
    ) = apolloClient.query(
        SearchCharacterQuery(page, search)
    ).await()

}