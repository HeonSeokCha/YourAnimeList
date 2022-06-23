package com.chs.youranimelist.domain.usecase

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
    suspend operator fun invoke(
        page: Int,
        selectedSort: MediaSort,
        selectedYear: Int,
        selectGenre: String?
    ): Flow<Resource<NoSeasonQuery.Page>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    repository.getNoSeasonList(
                        page,
                        selectedSort,
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