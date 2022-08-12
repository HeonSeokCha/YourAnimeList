package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Int,
        search: String
    ): Flow<Resource<SearchAnimeQuery.Data>> = repository.searchAnime(page, search)
}