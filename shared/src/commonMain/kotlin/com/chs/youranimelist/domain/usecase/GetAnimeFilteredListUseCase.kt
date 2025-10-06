package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeFilteredListUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(filter: SortFilter): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeFilteredList(filter)
    }
}