package com.chs.domain.usecase

import com.chs.domain.repository.SearchRepository
import javax.inject.Inject

class DeleteSearchHistoryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        searchRepository.deleteSearchHistory(title)
    }
}