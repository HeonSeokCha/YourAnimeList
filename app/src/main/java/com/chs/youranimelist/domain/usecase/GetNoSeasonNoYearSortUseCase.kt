package com.chs.youranimelist.domain.usecase

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
        page: Int,
        selectedSort: MediaSort,
        selectGenre: String?
    ): Flow<Resource<NoSeasonNoYearQuery.Page>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    repository.getNoSeasonNoYearList(
                        page,
                        selectedSort,
                        selectGenre
                    ).data!!.page
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}