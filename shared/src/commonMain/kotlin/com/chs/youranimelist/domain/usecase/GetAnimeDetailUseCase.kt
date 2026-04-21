package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class GetAnimeDetailUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): DataResult<AnimeDetailInfo, DataError.RemoteError> {
        return repository.getAnimeDetailInfo(animeId)
    }
}