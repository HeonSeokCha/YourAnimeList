package com.chs.domain.usecase

import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetSavedCharaInfoUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int): Flow<CharacterInfo?> {
        return repository.getSavedMediaInfo(id)
    }
}