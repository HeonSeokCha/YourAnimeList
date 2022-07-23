package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeOverViewThemeUseCase @Inject constructor(
    private val repository: AnimeDetailRepository
) {
    suspend operator fun invoke(animeMalId: Int): Flow<Resource<AnimeDetails>> =
        repository.getAnimeOverviewTheme(animeMalId)
}