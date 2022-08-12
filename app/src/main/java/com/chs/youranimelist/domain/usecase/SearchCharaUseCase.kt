package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharaUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Int,
        search: String
    ): Flow<Resource<SearchCharacterQuery.Data>> = repository.searchCharacter(page, search)
}