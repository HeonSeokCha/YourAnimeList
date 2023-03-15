package com.chs.domain.usecase

import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): AnimeDetailInfo {
        return repository.getAnimeDetailInfo(animeId)
    }
}