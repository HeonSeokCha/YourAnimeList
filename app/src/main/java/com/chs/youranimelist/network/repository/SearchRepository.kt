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

    fun getAnimeSearch(search: String): Flow<SearchAnimeQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchAnimeQuery(search = search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }

    fun getMangaSearch(search: String): Flow<SearchMangaQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchMangaQuery(search = search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }

    fun getCharacterSearch(search: String): Flow<SearchCharacterQuery.Data> {
        return ApolloServices.apolloClient.query(
            SearchCharacterQuery(search = search.toInput())
        ).toFlow().map {
            it.data!!
        }
    }
}