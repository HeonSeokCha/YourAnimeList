package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.CharacterRepository

class InsertCharaInfoUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(info: CharacterInfo) {
        return repository.insertMediaInfo(info)
    }
}