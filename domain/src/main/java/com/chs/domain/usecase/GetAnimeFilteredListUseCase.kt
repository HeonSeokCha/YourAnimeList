package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeFilteredListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(
        selectType: String,
        sortType: String,
        season: String,
        year: Int,
        genre: String?
    ): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeFilteredList(
            selectType,
            sortType,
            season,
            year,
            genre
        )
    }
}