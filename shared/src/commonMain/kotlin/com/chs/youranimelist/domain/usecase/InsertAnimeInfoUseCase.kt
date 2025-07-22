package com.chs.youranimelist.domain.usecase

import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository

class InsertAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(info: AnimeInfo) {
        repository.insertMediaInfo(info)
    }
}