package com.chs.domain.usecase

import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import javax.inject.Inject

class InsertCharaInfoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaInfo: CharacterInfo) {
        return repository.insertCharacterInfo(charaInfo)
    }
}