package com.chs.presentation.usecase

class GetRecommendAnimesUseCase(
    private val repository: com.chs.presentation.repository.AnimeRepository
) {
    suspend operator fun invoke(): com.chs.presentation.model.AnimeRecommendList {
        return repository.getAnimeRecommendList()
    }
}