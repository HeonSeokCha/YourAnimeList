package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
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

    override fun searchAnime(page: Int, search: String) = apolloClient.query(
        SearchAnimeQuery(page.toInput(), search.toInput())
    ).toFlow()

    override fun searchManga(page: Int, search: String) = apolloClient.query(
        SearchMangaQuery(page.toInput(), search.toInput())
    ).toFlow()

    override fun searchCharacter(page: Int, search: String) = apolloClient.query(
        SearchCharacterQuery(page.toInput(), search.toInput())
    ).toFlow()
}