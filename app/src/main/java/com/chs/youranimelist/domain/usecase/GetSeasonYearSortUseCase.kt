package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonYearSortUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(
        selectType: String,
        sort: MediaSort,
        season: MediaSeason?,
        seasonYear: Int?,
        genre: String?
    ): Flow<PagingData<AnimeList>> {
        return repository.getAnimeList(
            selectType = selectType,
            sort = sort,
            season = season,
            seasonYear = seasonYear,
            genre = genre
        )
    }
}