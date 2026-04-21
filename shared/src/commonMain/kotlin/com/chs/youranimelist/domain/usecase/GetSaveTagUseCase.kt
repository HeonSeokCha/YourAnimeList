package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.AnimeRepository
import org.koin.core.annotation.Single

@Single
class GetSaveTagUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): List<Pair<String, String?>> {
        return repository.getSavedTagList()
    }
}