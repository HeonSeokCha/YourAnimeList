package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchYourCharListUseCase @Inject constructor(
    private val repository: YourCharacterListRepository
) {
    operator fun invoke(searchTitle: String): Flow<List<Character>> {
        return repository.searchCharaList(searchTitle)
    }
}