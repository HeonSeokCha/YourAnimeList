package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
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

    fun getAnimeList(
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String?
    ): Flow<PagingData<AnimeListQuery.Medium>>


    fun getNoSeasonNoYearList(
        sort: MediaSort,
        genre: String?
    ): Flow<PagingData<NoSeasonNoYearQuery.Medium>>


    fun getNoSeasonList(
        sort: MediaSort,
        seasonYear: Int,
        genre: String?
    ): Flow<PagingData<NoSeasonQuery.Medium>>


    suspend fun getGenre(): ApolloResponse<GenreQuery.Data>

    fun checkAnimeList(animeId: Int): Flow<AnimeDto?>

    fun getYourAnimeList(): Flow<List<AnimeDto>>

    suspend fun insertAnime(anime: AnimeDto)

    suspend fun deleteAnime(animeId: AnimeDto)

    fun searchAnimeList(animeTitle: String): Flow<List<AnimeDto>>
}