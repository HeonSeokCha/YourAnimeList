package com.chs.domain.usecase

import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetRecentGenresTagUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke() {
        return repository.getRecentGenreTagList()
    }
}