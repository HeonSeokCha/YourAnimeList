package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetAnimeRecListUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(
        currentSeason: SeasonType,
        nextSeason: SeasonType,
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