package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class GetRecentGenresTagUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke() {
        return repository.getRecentGenreTagList()
    }
}