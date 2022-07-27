package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.repository.CharacterRepository
import javax.inject.Inject

class InsertCharaUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterDto) {
        repository.insertCharacter(character)
    }
}