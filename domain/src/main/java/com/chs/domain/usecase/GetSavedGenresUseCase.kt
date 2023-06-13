package com.chs.domain.usecase

import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetSavedGenresUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSavedGenreList()
    }
}