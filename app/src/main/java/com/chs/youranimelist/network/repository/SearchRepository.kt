package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.network.services.ApolloServices

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