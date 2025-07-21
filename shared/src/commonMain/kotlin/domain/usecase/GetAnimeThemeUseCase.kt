package domain.usecase

import com.chs.domain.model.AnimeThemeInfo
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.domain.repository.AnimeRepository

class GetAnimeThemeUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): DataResult<AnimeThemeInfo, DataError.RemoteError> {
        return repository.getAnimeDetailTheme(animeId)
    }
}