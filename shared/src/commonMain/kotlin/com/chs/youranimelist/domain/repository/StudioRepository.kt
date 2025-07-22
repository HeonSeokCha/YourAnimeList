package com.chs.youranimelist.domain.repository

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    suspend fun getStudioDetailInfo(studioId: Int): DataResult<StudioDetailInfo, DataError.RemoteError>

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>>
}