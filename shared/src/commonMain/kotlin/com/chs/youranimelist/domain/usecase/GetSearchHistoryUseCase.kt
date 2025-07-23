package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getSearchHistory()
    }
}