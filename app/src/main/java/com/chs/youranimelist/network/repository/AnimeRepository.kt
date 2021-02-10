package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeRecListQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class AnimeRepository {

    @ExperimentalCoroutinesApi
    fun getAnimeRecList(): Flow<AnimeRecListQuery.Data> {
        return AnimeService.apolloClient.query(AnimeRecListQuery()).toFlow().map {
            it.data!!
        }
    }

    @ExperimentalCoroutinesApi
    fun getAnimeInfo(animeId: Input<Int>): Flow<AnimeDetailQuery.Data> {
        return AnimeService.apolloClient.query(AnimeDetailQuery(animeId)).toFlow().map {
            it.data!!
        }
    }
}