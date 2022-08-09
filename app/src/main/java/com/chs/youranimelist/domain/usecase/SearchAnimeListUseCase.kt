package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.repository.AnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAnimeListUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(searchTitle: String): Flow<List<AnimeDto>> =
        repository.searchAnimeList(searchTitle)
}