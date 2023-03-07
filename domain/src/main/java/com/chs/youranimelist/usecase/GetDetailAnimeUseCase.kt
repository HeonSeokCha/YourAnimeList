package com.chs.youranimelist.usecase

import com.chs.youranimelist.model.AnimeDetailInfo
import com.chs.youranimelist.repository.AnimeRepository

class GetDetailAnimeUseCase(
    private val repository: com.chs.youranimelist.repository.AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): com.chs.youranimelist.model.AnimeDetailInfo {
        return repository.getAnimeDetailInfo(animeId)
    }
}