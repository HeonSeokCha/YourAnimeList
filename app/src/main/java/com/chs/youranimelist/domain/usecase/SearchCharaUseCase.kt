package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharaUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(
        search: String
    ): Flow<PagingData<SearchCharacterQuery.Character>> = repository.searchCharacter(search)
}