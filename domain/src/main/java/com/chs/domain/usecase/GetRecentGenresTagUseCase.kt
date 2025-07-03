package com.chs.domain.usecase

import com.chs.domain.repository.AnimeRepository

class GetRecentGenresTagUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke() {
        return repository.getRecentGenreTagList()
    }
}