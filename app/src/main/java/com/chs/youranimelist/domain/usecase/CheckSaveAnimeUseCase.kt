package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.repository.AnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckSaveAnimeUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(animeId: Int): Flow<AnimeDto?> =
        repository.checkAnimeList(animeId)
}