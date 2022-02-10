package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSaveCharaUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    operator fun invoke(charaId: Int): Flow<Character?> =
        repository.checkCharaList(charaId)
}