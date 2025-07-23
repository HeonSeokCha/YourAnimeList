package com.chs.youranimelist.domain.usecase

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.StudioRepository
import kotlinx.coroutines.flow.Flow

class GetStudioAnimeListUseCase(
    private val repository: StudioRepository
) {
    operator fun invoke(
        studioId: Int,
        studioSort: String
    ): Flow<PagingData<AnimeInfo>> {
        return repository.getStudioAnimeList(
            studioId = studioId,
            studioSort = studioSort
        )
    }
}