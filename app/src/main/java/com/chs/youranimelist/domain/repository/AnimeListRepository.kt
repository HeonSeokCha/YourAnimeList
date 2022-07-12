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

    suspend fun getAnimeList(
        page: Int,
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String?
    ): ApolloResponse<AnimeListQuery.Data>


    suspend fun getNoSeasonNoYearList(
        page: Int,
        sort: MediaSort,
        genre: String?
    ): ApolloResponse<NoSeasonNoYearQuery.Data>


    suspend fun getNoSeasonList(
        page: Int,
        sort: MediaSort,
        seasonYear: Int,
        genre: String?
    ): ApolloResponse<NoSeasonQuery.Data>


    suspend fun getGenre(): ApolloResponse<GenreQuery.Data>

    fun checkAnimeList(animeId: Int): Flow<AnimeDto?>

    suspend fun insertAnime(anime: AnimeDto)

    suspend fun deleteAnime(anime: AnimeDto)
}