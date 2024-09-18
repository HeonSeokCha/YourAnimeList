package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeFilteredListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(
        sortType: List<String>,
        season: String?,
        year: Int?,
        genres: List<String>?,
        tags: List<String>?,
        status: String?
    ): Flow<PagingData<AnimeInfo>> {
        return repository.getAnimeFilteredList(
            sortType = sortType,
            season = season,
            year = year,
            genres = genres,
            tags = tags,
            status = status
        )
    }
}