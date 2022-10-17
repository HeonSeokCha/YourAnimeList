package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoSeasonNoYearSortUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(
        selectedSort: MediaSort,
        selectGenre: String?
    ): Flow<PagingData<NoSeasonNoYearQuery.Medium>> {
        return repository.getNoSeasonNoYearList(
            sort = selectedSort,
            genre = selectGenre
        )
    }
}