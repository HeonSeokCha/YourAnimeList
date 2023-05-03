package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.StudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudioAnimeListUseCase @Inject constructor(
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