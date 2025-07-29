package com.chs.youranimelist.domain.repository

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.StudioDetailInfo
import kotlinx.coroutines.flow.Flow

interface StudioRepository {

    suspend fun getStudioDetailInfo(studioId: Int): DataResult<StudioDetailInfo, DataError.RemoteError>

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>>
}