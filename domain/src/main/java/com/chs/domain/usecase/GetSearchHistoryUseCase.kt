package com.chs.domain.usecase

import com.chs.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSearchHistory()
    }
}