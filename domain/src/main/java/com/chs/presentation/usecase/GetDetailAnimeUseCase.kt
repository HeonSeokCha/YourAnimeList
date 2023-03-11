package com.chs.presentation.usecase

class GetDetailAnimeUseCase(
    private val repository: com.chs.presentation.repository.AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): com.chs.presentation.model.AnimeDetailInfo {
        return repository.getAnimeDetailInfo(animeId)
    }
}