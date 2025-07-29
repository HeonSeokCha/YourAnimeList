package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeThemeInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetAnimeThemeUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): DataResult<AnimeThemeInfo, DataError.RemoteError> {
        return repository.getAnimeDetailTheme(animeId)
    }
}