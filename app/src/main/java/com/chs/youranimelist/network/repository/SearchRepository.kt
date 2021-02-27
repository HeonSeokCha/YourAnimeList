package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.MediaSearchQuery
import com.chs.youranimelist.network.ApolloServices
import com.chs.youranimelist.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepository {

    fun getAnimeSearch(
        search: String,
        type: MediaType,
    ): Flow<MediaSearchQuery.Data> {
        return ApolloServices.apolloClient.query(MediaSearchQuery(search.toInput(), type.toInput()))
            .toFlow().map {
                it.data!!
            }
    }

    fun getMangaSearch(
        search: String,
        type: MediaType,
    ): Flow<MediaSearchQuery.Data> {
        return ApolloServices.apolloClient.query(MediaSearchQuery(search.toInput(), type.toInput()))
            .toFlow().map {
                it.data!!
            }
    }

    fun getCharacterSearch() {

    }
}