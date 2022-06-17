package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.*
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    suspend fun getHomeRecommendList(): ApolloResponse<HomeRecommendListQuery.Data>

    fun getAnimeLists(): Flow<List<AnimeDto>>

    suspend fun insertAnime(anime: AnimeDto)

    suspend fun deleteAnime(anime: AnimeDto)
}