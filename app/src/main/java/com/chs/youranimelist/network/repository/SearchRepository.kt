package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchCharacterQuery
import com.chs.youranimelist.search.SearchMangaQuery

class SearchRepository {

    fun searchAnime(page: Int, search: String) = ApolloServices.apolloClient.query(
        SearchAnimeQuery(page.toInput(), search.toInput())
    ).toFlow()

    fun searchManga(page: Int, search: String) = ApolloServices.apolloClient.query(
        SearchMangaQuery(page.toInput(), search.toInput())
    ).toFlow()

    fun searchCharacter(page: Int, search: String) = ApolloServices.apolloClient.query(
        SearchCharacterQuery(page.toInput(), search.toInput())
    ).toFlow()
}