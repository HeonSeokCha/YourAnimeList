package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.StudioRepository
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