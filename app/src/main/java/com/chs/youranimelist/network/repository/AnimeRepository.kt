package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.network.api.AnimeService
import kotlinx.coroutines.Dispatchers
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AnimeRepository {

    @ExperimentalCoroutinesApi
    fun getAnimeInfo(animeId: Input<Int>): Flow<AnimeDetailQuery.Data> {
        return AnimeService.apolloClient.query(AnimeDetailQuery(animeId)).toFlow().map {
                it.data!!
        }
    }

    @ExperimentalCoroutinesApi
    fun getAnimeRecList(): Flow<AnimeRecListQuery.Data> {
        return AnimeService.apolloClient.query(AnimeRecListQuery()).toFlow().map {
            it.data!!
        }
    }

    @ExperimentalCoroutinesApi
    fun getAnimeList (
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: Input<MediaSeason>,
        seasonYear: Input<Int>
    ): Flow<AnimeListQuery.Data> {
        return AnimeService.apolloClient.query(
            AnimeListQuery(page, sort, season, seasonYear)).toFlow().map {
            it.data!!
        }
    }
}