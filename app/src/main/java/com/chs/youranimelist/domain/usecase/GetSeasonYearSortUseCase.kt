package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonYearSortUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    suspend operator fun invoke(
        page: Int,
        selectedSort: MediaSort,
        selectedSeason: MediaSeason,
        selectedYear: Int,
        selectGenre: String?
    ): Flow<Resource<AnimeListQuery.Page>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    repository.getAnimeList(
                        page,
                        selectedSort,
                        selectedSeason,
                        selectedYear,
                        selectGenre
                    ).data!!.page
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}