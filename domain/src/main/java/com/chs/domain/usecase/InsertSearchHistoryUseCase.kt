package com.chs.domain.usecase

import com.chs.domain.repository.SearchRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        repository.insertSearchHistory(title)
    }
}