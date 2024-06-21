package com.chs.domain.repository

import androidx.paging.PagingData
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    fun getStudioDetailInfo(studioId: Int): Flow<Resource<StudioDetailInfo>>

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>>
}