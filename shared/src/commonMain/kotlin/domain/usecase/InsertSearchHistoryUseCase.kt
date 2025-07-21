package domain.usecase

import com.chs.domain.repository.SearchRepository

class InsertSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(title: String) {
        repository.insertSearchHistory(title)
    }
}