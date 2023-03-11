package com.chs.presentation.repository

import androidx.paging.PagingData
import com.chs.presentation.StudioAnimeQuery
import com.chs.presentation.type.MediaSort
import kotlinx.coroutines.flow.Flow

interface StudioRepository {
    fun getStudioAnime(
        studioId: Int,
        sort: MediaSort,
    ): Flow<PagingData<StudioAnimeQuery.Data>>
}