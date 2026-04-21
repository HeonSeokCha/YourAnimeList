package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.repository.SearchRepository
import org.koin.core.annotation.Single

@Single
class InsertSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        repository.insertSearchHistory(title)
    }
}