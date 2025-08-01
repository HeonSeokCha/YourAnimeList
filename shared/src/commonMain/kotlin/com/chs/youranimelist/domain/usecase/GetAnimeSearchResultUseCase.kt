package com.chs.youranimelist.domain.usecase

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeSearchResultUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeSearchResult(query)
    }
}