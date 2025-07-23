package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetAnimeRecListUseCase(
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