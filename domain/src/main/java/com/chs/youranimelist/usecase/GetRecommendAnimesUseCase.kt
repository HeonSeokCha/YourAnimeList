package com.chs.youranimelist.usecase

import com.chs.youranimelist.model.AnimeRecommendList
import com.chs.youranimelist.repository.AnimeRepository

class GetRecommendAnimesUseCase(
    private val repository: com.chs.youranimelist.repository.AnimeRepository
) {
    suspend operator fun invoke(): com.chs.youranimelist.model.AnimeRecommendList {
        return repository.getAnimeRecommendList()
    }
}