package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetSavedAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(id: Int): Flow<AnimeInfo?> {
        return repository.getSavedMediaInfo(id)
    }
}