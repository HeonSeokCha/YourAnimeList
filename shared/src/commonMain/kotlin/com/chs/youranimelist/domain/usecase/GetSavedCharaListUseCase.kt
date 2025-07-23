package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetSavedCharaListUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<List<CharacterInfo>> {
        return repository.getSavedMediaInfoList()
    }
}