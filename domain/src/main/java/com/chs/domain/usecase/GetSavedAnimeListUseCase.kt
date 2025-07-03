package com.chs.domain.usecase

import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetSavedAnimeListUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(): Flow<List<AnimeInfo>> {
        return repository.getSavedMediaInfoList()
    }
}