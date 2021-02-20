package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.*
import com.chs.youranimelist.network.ApolloServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AnimeRepository {

    fun getAnimeRecList(): Flow<HomeRecommendListQuery.Data> {
        return ApolloServices.apolloClient.query(HomeRecommendListQuery()).toFlow().map {
            it.data!!
        }
    }

    fun getAnimeInfo(animeId: Input<Int>): Flow<AnimeDetailQuery.Data> {
        return ApolloServices.apolloClient.query(AnimeDetailQuery(animeId)).toFlow().map {
            it.data!!
        }
    }

    fun getAnimeOverview(animeId: Input<Int>): Flow<AnimeOverviewQuery.Data> {
        return ApolloServices.apolloClient.query(AnimeOverviewQuery(animeId)).toFlow().map {
            it.data!!
        }
    }

    fun getAnimeCharacter(animeId: Input<Int>): Flow<AnimeCharacterQuery.Data> {
        return ApolloServices.apolloClient.query(AnimeCharacterQuery(animeId)).toFlow().map {
            it.data!!
        }
    }
}