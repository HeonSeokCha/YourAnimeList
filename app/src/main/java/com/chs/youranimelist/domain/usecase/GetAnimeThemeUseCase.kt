package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeThemeUseCase @Inject constructor(
    private val repository: AnimeDetailRepository
) {
    suspend operator fun invoke(
        animeIdMal: Int
    ): Flow<Resource<AnimeDetails>> {
        return repository.getAnimeOverviewTheme(animeIdMal)
    }
}