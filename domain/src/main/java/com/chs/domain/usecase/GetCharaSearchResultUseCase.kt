package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.CharacterRepository
import com.chs.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharaSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<CharacterInfo>> {
        return repository.getCharacterSearchResult(query)
    }
}