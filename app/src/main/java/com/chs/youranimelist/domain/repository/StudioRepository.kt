package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.apollographql.apollo3.api.ApolloResponse
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.flow.Flow

interface StudioRepository {
    fun getStudioAnime(
        studioId: Int,
        sort: MediaSort,
    ): Flow<PagingData<StudioAnimeQuery.Data>>
}