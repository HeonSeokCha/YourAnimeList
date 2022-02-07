package com.chs.youranimelist.data.remote.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.chs.youranimelist.sortedlist.AnimeListQuery
import com.chs.youranimelist.sortedlist.GenreQuery
import com.chs.youranimelist.sortedlist.NoSeasonNoYearQuery
import com.chs.youranimelist.sortedlist.NoSeasonQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {
    fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>,
        genre: Input<String>
    ): Flow<Response<AnimeListQuery.Data>>


    fun getNoSeasonNoYearList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        genre: Input<String>
    ): Flow<Response<NoSeasonNoYearQuery.Data>>


    fun getNoSeasonList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        seasonYear: Input<Int>,
        genre: Input<String>
    ): Flow<Response<NoSeasonQuery.Data>>


    fun getGenre(): Flow<Response<GenreQuery.Data>>
}