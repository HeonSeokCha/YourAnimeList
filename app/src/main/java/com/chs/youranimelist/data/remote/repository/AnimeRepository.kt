package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.home.HomeRecommendListQuery


interface AnimeRepository {

    suspend fun getHomeRecommendList(): Response<HomeRecommendListQuery.Data>

    suspend fun getAnimeDetail(animeId: Input<Int>): Response<AnimeDetailQuery.Data>

    suspend fun getAnimeOverview(animeId: Input<Int>): Response<AnimeOverviewQuery.Data>

    suspend fun getAnimeCharacter(animeId: Input<Int>): Response<AnimeCharacterQuery.Data>

    suspend fun getAnimeRecList(
        animeId: Input<Int>,
        page: Input<Int>
    ): Response<AnimeRecommendQuery.Data>

    suspend fun getAnimeOverviewTheme(animeId: Int): AnimeDetails?
}