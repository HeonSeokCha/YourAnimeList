package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import javax.inject.Inject

class DeleteCharaUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.deleteCharaList(character)
    }
}