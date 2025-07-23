package com.chs.youranimelist.domain.usecase

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetCharaSearchResultUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<CharacterInfo>> {
        return repository.getCharacterSearchResult(query)
    }
}