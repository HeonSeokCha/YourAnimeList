package com.chs.domain.usecase

import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetSavedAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(id: Int): Flow<AnimeInfo?> {
        return repository.getSavedMediaInfo(id)
    }
}