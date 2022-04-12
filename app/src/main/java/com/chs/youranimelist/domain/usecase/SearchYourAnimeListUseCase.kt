package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.Anime
import com.chs.youranimelist.domain.repository.YourAnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchYourAnimeListUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    operator fun invoke(searchTitle: String): Flow<List<Anime>> =
        repository.searchAnimeList(searchTitle)
}