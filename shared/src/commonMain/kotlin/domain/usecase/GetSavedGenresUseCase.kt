package domain.usecase

import com.chs.domain.repository.AnimeRepository

class GetSavedGenresUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSavedGenreList()
    }
}