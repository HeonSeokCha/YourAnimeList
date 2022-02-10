package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import javax.inject.Inject

class InsertCharaUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.insertCharaList(character)
    }
}