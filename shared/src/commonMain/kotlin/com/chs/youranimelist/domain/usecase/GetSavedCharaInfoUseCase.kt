package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetSavedCharaInfoUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int): Flow<CharacterInfo?> {
        return repository.getSavedMediaInfo(id)
    }
}