package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetCharaSearchResultUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<CharacterInfo>> {
        return repository.getCharacterSearchResult(query)
    }
}