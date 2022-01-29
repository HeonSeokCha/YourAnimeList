package com.chs.youranimelist.network.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.*
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.network.services.JikanRestService
import com.chs.youranimelist.util.ConvertDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeRepository(
    private val apolloClient: ApolloClient
) {

    fun getHomeRecList() = apolloClient.query(
        HomeRecommendListQuery(
            ConvertDate.getCurrentSeason().toInput(),
            ConvertDate.getNextSeason().toInput(),
            ConvertDate.getCurrentYear(false).toInput(),
            ConvertDate.getCurrentYear(true).toInput()
        )
    ).toFlow().flowOn(Dispatchers.IO)


    fun getAnimeDetail(animeId: Input<Int>) =
        apolloClient.query(AnimeDetailQuery(animeId)).toFlow()

    fun getAnimeOverview(animeId: Input<Int>) =
        apolloClient.query(AnimeOverviewQuery(animeId)).toFlow()

    fun getAnimeCharacter(animeId: Input<Int>) =
        apolloClient.query(AnimeCharacterQuery(animeId)).toFlow()

    fun getAnimeRecList(animeId: Input<Int>, page: Input<Int>) =
        apolloClient.query(AnimeRecommendQuery(animeId, page)).toFlow()

    suspend fun getAnimeOverviewTheme(animeId: Int) =
        JikanRestService.create().getAnimeTheme(animeId)
}