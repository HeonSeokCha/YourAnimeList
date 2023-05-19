package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAnimeRecListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        lastYear: Int,
        nextYear: Int
    ): Flow<Resource<AnimeRecommendList>> {
        return flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repository.getAnimeRecommendList(
                            currentSeason,
                            nextSeason,
                            currentYear,
                            lastYear,
                            nextYear
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}