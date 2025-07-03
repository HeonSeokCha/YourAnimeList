package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeDetailRecListUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Int): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeDetailInfoRecommendList(animeId)
    }
}