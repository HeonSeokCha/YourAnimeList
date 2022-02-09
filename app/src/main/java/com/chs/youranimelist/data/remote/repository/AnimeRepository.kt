package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.dto.AnimeDetails
import com.chs.youranimelist.home.HomeRecommendListQuery
import kotlinx.coroutines.flow.Flow


interface AnimeRepository {

    suspend fun getHomeRecommendList(): Response<HomeRecommendListQuery.Data>

    fun getAnimeDetail(animeId: Input<Int>): Flow<Response<AnimeDetailQuery.Data>>

    fun getAnimeOverview(animeId: Input<Int>): Flow<Response<AnimeOverviewQuery.Data>>

    fun getAnimeCharacter(animeId: Input<Int>): Flow<Response<AnimeCharacterQuery.Data>>

    fun getAnimeRecList(
        animeId: Input<Int>,
        page: Input<Int>
    ): Flow<Response<AnimeRecommendQuery.Data>>

    suspend fun getAnimeOverviewTheme(animeId: Int): AnimeDetails?
}