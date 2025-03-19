package com.chs.domain.usecase

import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.DataResult
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): DataResult<AnimeDetailInfo, DataError.RemoteError> {
        return repository.getAnimeDetailInfo(animeId)
    }
}