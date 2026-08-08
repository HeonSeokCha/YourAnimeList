package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeSavedInfo
import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class InsertAnimeInfoUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(info: AnimeSavedInfo) {
        repository.insertMediaInfo(info)
    }
}