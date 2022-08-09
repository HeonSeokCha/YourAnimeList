package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharaListUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(searchTitle: String): Flow<List<CharacterDto>> {
        return repository.searchCharaList(searchTitle)
    }
}