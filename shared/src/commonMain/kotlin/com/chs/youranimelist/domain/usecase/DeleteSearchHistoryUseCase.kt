package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.SearchRepository

class DeleteSearchHistoryUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        searchRepository.deleteSearchHistory(title)
    }
}