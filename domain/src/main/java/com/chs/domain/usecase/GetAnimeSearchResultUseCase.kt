package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeSearchResultUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(query: String): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeSearchResult(query)
    }
}