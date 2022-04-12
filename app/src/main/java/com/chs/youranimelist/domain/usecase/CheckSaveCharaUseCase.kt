package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckSaveCharaUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    operator fun invoke(charaId: Int): Flow<Character?> =
        repository.checkCharaList(charaId)
}