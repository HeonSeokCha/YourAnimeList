package com.chs.domain.usecase

import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedSearchAnimeListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(query: String): Flow<List<AnimeInfo>> {
        return repository.getSavedSearchAnimeList(query)
    }
}