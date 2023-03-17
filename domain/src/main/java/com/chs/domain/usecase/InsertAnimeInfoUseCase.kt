package com.chs.domain.usecase

import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class InsertAnimeInfoUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeInfo: AnimeInfo) {
        repository.insertSavedAnimeInfo(animeInfo)
    }
}