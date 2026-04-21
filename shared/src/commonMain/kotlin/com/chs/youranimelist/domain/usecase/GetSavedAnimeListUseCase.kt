package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetSavedAnimeListUseCase(
    private val repository: AnimeRepository
) {
    operator fun invoke(): Flow<List<AnimeInfo>> {
        return repository.getSavedMediaInfoList()
    }
}