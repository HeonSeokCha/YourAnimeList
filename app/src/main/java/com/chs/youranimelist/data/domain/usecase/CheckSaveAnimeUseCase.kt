package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSaveAnimeUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    operator fun invoke(animeId: Int): Flow<Anime?> =
        repository.checkAnimeList(animeId)
}