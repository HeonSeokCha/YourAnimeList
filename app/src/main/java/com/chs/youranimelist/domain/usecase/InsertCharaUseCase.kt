package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.domain.repository.YourCharacterListRepository
import javax.inject.Inject

class InsertCharaUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.insertCharaList(character)
    }
}