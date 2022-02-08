package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYourCharaListUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    operator fun invoke(): Flow<List<Character>> {
        return repository.getAllCharaList()
    }
}