package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeSearchResultUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeSearchResult(query)
    }
}