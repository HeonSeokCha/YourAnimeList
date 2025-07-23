package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository

class DeleteAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(info: AnimeInfo) {
        repository.deleteMediaInfo(info)
    }
}