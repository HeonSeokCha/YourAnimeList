package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.*
import com.chs.youranimelist.network.services.ApolloServices
import com.chs.youranimelist.network.services.JikanRestService
import com.chs.youranimelist.util.ConvertDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class AnimeRepository {

    fun getHomeRecList() = ApolloServices.apolloClient.query(
        HomeRecommendListQuery(
            ConvertDate.getCurrentSeason().toInput(),
            ConvertDate.getNextSeason().toInput(),
            ConvertDate.getCurrentYear(false).toInput(),
            ConvertDate.getCurrentYear(true).toInput()
        )
    ).toFlow().flowOn(Dispatchers.IO)


    fun getAnimeDetail(animeId: Input<Int>) =
        ApolloServices.apolloClient.query(AnimeDetailQuery(animeId)).toFlow()

    fun getAnimeOverview(animeId: Input<Int>) =
        ApolloServices.apolloClient.query(AnimeOverviewQuery(animeId)).toFlow()

    fun getAnimeCharacter(animeId: Input<Int>) =
        ApolloServices.apolloClient.query(AnimeCharacterQuery(animeId)).toFlow()

    fun getAnimeRecList(animeId: Input<Int>, page: Input<Int>) =
        ApolloServices.apolloClient.query(AnimeRecommendQuery(animeId, page)).toFlow()

    suspend fun getAnimeOverviewTheme(animeId: Int) =
        JikanRestService.create().getAnimeTheme(animeId)
}