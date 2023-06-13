package com.chs.domain.usecase

import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetRecentGenresUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke() {
        return repository.getRecentGenreList()
    }
}