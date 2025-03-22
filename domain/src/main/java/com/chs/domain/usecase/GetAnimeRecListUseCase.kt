package com.chs.domain.usecase

import com.chs.domain.model.AnimeRecommendList
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeRecListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(
        currentSeason: String,
        nextSeason: String,
        currentYear: Int,
        variationYear: Int
    ): DataResult<AnimeRecommendList, DataError.RemoteError> {
        return repository.getAnimeRecommendList(
            currentSeason = currentSeason,
            nextSeason = nextSeason,
            currentYear = currentYear,
            variationYear = variationYear
        )
    }
}