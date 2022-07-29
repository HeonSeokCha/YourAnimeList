package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYourCharaListUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<List<CharacterDto>> =
        repository.getYourCharaList()
}