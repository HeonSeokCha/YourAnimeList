package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.network.ApolloServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepository {

    fun getAnimeSearch(page: Int, search: String): Flow<SearchAnimeQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchAnimeQuery(page.toInput(), search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }

    fun getMangaSearch(page: Int, search: String): Flow<SearchMangaQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchMangaQuery(page.toInput(), search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }

    fun getCharacterSearch(page: Int, search: String): Flow<SearchCharacterQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchCharacterQuery(page.toInput(), search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }
}