package com.chs.youranimelist.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.GenreQuery
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {
//    suspend fun getAnimeRecommendList(
//        page: Int,
//        sort: MediaSort,
//        season: MediaSeason,
//        seasonYear: Int,
//        genre: String?
//    ): Flow<ApolloResponse<AnimeListQuery.Data>>
//
//    suspend fun getNoSeasonNoYearList(
//        page: Int,
//        sort: MediaSort,
//        genre: String?
//    ): Flow<ApolloResponse<NoSeasonNoYearQuery.Data>>
//
//
//    suspend fun getNoSeasonList(
//        page: Int,
//        sort: MediaSort,
//        seasonYear: Int,
//        genre: String?
//    ): Flow<ApolloResponse<NoSeasonQuery.Data>>
//
//
//    suspend fun getGenre(): Flow<ApolloResponse<GenreQuery.Data>>

    fun getAnimeLists(): Flow<List<AnimeDto>>

    suspend fun insertAnime(anime: AnimeDto)

    suspend fun deleteAnime(anime: AnimeDto)
}