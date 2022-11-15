package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.chs.youranimelist.*
import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeDetailRepository {

    suspend fun getAnimeDetail(animeId: Int): Flow<Resource<AnimeDetailQuery.Data>>

    fun getAnimeRecList(animeId: Int): Flow<PagingData<AnimeRecommendQuery.Edge>>

    suspend fun getAnimeOverviewTheme(animeId: Int): Flow<Resource<AnimeDetails>>
}