package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckSaveCharaUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(charaId: Int): Flow<CharacterDto?> {
        return repository.checkCharaList(charaId)
    }
}