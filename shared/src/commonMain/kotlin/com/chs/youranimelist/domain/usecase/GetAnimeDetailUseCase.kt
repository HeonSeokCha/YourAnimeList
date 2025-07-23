package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetAnimeDetailUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): DataResult<AnimeDetailInfo, DataError.RemoteError> {
        return repository.getAnimeDetailInfo(animeId)
    }
}