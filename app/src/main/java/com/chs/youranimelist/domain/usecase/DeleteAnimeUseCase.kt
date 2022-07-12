package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.mapper.toAnimeDto
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.domain.repository.AnimeListRepository
import javax.inject.Inject

class DeleteAnimeUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    suspend operator fun invoke(anime: Anime) {
        repository.deleteAnime(anime.toAnimeDto())
    }
}