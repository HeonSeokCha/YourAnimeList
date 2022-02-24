package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort

interface AnimeListRepository {
    suspend fun getAnimeList(
        page: Int,
        sort: MediaSort,
        season: MediaSeason,
        seasonYear: Int,
        genre: String
    ): ApolloResponse<AnimeListQuery.Data>


    suspend fun getNoSeasonNoYearList(
        page: Int,
        sort: MediaSort,
        genre: String
    ): ApolloResponse<NoSeasonNoYearQuery.Data>


    suspend fun getNoSeasonList(
        page: Int,
        sort: MediaSort,
        seasonYear: Int,
        genre: String
    ): ApolloResponse<NoSeasonQuery.Data>


    suspend fun getGenre(): ApolloResponse<GenreQuery.Data>
}