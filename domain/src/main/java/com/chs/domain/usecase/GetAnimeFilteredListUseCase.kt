package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.SortFilter
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeFilteredListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(filter: SortFilter): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeFilteredList(filter)
    }
}