package com.chs.domain.usecase

import com.chs.domain.repository.SearchRepository

class DeleteSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        searchRepository.deleteSearchHistory(title)
    }
}