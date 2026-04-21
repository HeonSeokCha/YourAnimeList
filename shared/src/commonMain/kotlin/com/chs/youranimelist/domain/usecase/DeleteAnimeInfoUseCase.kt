package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class DeleteAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(info: AnimeInfo) {
        repository.deleteMediaInfo(info)
    }
}