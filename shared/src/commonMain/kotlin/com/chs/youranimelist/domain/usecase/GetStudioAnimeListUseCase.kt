package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.StudioRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
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