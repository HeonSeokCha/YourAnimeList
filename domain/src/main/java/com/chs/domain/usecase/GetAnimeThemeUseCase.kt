package com.chs.domain.usecase

import com.chs.domain.model.AnimeThemeInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.Result
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeThemeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): Result<AnimeThemeInfo, DataError.RemoteError> {
        return repository.getAnimeDetailTheme(animeId)
    }
}