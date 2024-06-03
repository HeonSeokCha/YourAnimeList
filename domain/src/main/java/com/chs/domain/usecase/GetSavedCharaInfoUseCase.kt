package com.chs.domain.usecase

import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedCharaInfoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int): Flow<CharacterInfo?> {
        return repository.getSavedMediaInfo(id)
    }
}