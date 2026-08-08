package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeSavedInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class DeleteAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(info: AnimeSavedInfo) {
        repository.deleteMediaInfo(info)
    }
}