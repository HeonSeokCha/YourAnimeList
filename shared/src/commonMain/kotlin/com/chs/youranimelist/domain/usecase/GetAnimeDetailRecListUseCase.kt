package com.chs.youranimelist.domain.usecase

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeDetailRecListUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Int): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeDetailInfoRecommendList(animeId)
    }
}