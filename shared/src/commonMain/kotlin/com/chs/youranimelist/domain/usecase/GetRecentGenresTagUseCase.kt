package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.AnimeRepository

class GetRecentGenresTagUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke() {
        return repository.getRecentGenreTagList()
    }
}