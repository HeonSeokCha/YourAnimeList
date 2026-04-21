package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.CharacterRepository
import org.koin.core.annotation.Single

@Single
class DeleteCharaInfoUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(info: CharacterInfo) {
        return repository.deleteMediaInfo(info)
    }
}