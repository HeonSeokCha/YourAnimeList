package com.chs.youranimelist.network.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.chs.youranimelist.ViewPagerQuery
import com.chs.youranimelist.network.api.AnimeService
import kotlinx.coroutines.Dispatchers
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeListQuery
import kotlinx.coroutines.flow.*

class AnimeListRepository {
    fun getAnimeViewPager(): Flow<ViewPagerQuery.Data> {
        return AnimeService.apolloClient.query(ViewPagerQuery()).toFlow().map {
            it.data!!
        }.flowOn(Dispatchers.IO)
    }

    fun getAnimeInfo(animeId: Input<Int>): Flow<AnimeDetailQuery.Data> {
        return AnimeService.apolloClient.query(AnimeDetailQuery(animeId)).toFlow().map {
                it.data!!
        }.flowOn(Dispatchers.IO)
    }

    fun getAnimeList(): Flow<AnimeListQuery.Data> {
        return AnimeService.apolloClient.query(AnimeListQuery()).toFlow().map {
            it.data!!
        }.flowOn(Dispatchers.IO)
    }
}