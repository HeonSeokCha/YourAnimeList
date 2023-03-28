package com.chs.domain.usecase

import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.repository.AnimeRepository
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
    ): AnimeRecommendList {
        return repository.getAnimeRecommendList(
            currentSeason,
            nextSeason,
            currentYear,
            lastYear,
            nextYear
        )
    }
}