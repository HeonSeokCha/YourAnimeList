package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.AnimeRepository

class GetSavedGenresUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSavedGenreList()
    }
}