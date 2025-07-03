package com.chs.domain.usecase

import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository

class InsertCharaInfoUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(info: CharacterInfo) {
        return repository.insertMediaInfo(info)
    }
}