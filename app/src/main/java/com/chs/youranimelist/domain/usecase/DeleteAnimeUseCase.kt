package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.Anime
import com.chs.youranimelist.domain.repository.YourAnimeListRepository
import javax.inject.Inject

class DeleteAnimeUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    suspend operator fun invoke(anime: Anime) {
        repository.deleteAnimeList(anime)
    }
}