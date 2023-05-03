package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    suspend fun getStudioDetailInfo(studioId: Int): StudioDetailInfo

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>>
}