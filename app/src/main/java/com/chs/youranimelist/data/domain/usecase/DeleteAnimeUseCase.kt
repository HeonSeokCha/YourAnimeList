package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import javax.inject.Inject

class DeleteAnimeUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    suspend operator fun invoke(anime: Anime) {
        repository.deleteAnimeList(anime)
    }
}