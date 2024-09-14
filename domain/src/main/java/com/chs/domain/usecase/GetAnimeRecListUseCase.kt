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
    operator fun invoke(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        variationYear: Int
    ): Flow<Resource<AnimeRecommendList>> {
        return repository.getAnimeRecommendList(
            currentSeason = currentSeason,
            nextSeason = nextSeason,
            currentYear = currentYear,
            variationYear = variationYear
        )
    }
}