package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYourCharaListUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    operator fun invoke(): Flow<List<Character>> {
        return repository.getAllCharaList()
    }
}