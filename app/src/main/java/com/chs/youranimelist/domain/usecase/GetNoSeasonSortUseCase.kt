package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoSeasonSortUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(
        selectedSort: MediaSort,
        selectedYear: Int,
        selectGenre: String?
    ): Flow<PagingData<NoSeasonQuery.Medium>> {
        return repository.getNoSeasonList(
            sort = selectedSort,
            seasonYear = selectedYear,
            genre = selectGenre
        )
    }
}