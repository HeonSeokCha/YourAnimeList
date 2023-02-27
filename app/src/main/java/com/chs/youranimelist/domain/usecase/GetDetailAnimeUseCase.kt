package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetDetailAnimeUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): AnimeDetailInfo {
        return repository.getAnimeDetailInfo(animeId)
    }
}