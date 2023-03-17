package com.chs.domain.usecase

import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeThemeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): AnimeThemeInfo {
        return repository.getAnimeDetailTheme(animeId)
    }
}