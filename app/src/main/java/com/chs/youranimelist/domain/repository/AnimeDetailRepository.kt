package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.*
import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeDetailRepository {

    suspend fun getAnimeDetail(animeId: Int): Flow<Resource<AnimeDetailQuery.Data>>

    suspend fun getAnimeOverview(animeId: Int): Flow<Resource<AnimeOverviewQuery.Data>>

    suspend fun getAnimeCharacter(animeId: Int): Flow<Resource<AnimeCharacterQuery.Data>>

    suspend fun getAnimeRecList(
        animeId: Int,
        page: Int
    ): Flow<Resource<AnimeRecommendQuery.Data>>

    suspend fun getAnimeOverviewTheme(animeId: Int): Flow<Resource<AnimeDetails>>
}