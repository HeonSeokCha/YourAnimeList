package com.chs.domain.usecase

import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import javax.inject.Inject

class DeleteCharaInfoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(info: CharacterInfo) {
        return repository.deleteMediaInfo(info)
    }
}