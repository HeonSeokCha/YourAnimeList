package com.chs.domain.usecase

import com.chs.domain.model.AnimeRecommendList
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject


class GetRecommendAnimesUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): AnimeRecommendList {
        return repository.getAnimeRecommendList()
    }
}