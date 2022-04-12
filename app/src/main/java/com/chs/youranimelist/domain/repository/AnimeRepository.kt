package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.model.AnimeDetails
import com.chs.youranimelist.home.HomeRecommendListQuery


interface AnimeRepository {

    suspend fun getHomeRecommendList(): ApolloResponse<HomeRecommendListQuery.Data>

    suspend fun getAnimeDetail(animeId: Int): ApolloResponse<AnimeDetailQuery.Data>

    suspend fun getAnimeOverview(animeId: Int): ApolloResponse<AnimeOverviewQuery.Data>

    suspend fun getAnimeCharacter(animeId: Int): ApolloResponse<AnimeCharacterQuery.Data>

    suspend fun getAnimeRecList(
        animeId: Int,
        page: Int
    ): ApolloResponse<AnimeRecommendQuery.Data>

    suspend fun getAnimeOverviewTheme(animeId: Int): AnimeDetails?
}