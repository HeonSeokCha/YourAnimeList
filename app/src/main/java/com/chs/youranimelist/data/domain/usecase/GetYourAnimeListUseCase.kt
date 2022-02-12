package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYourAnimeListUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    operator fun invoke(): Flow<List<Anime>> {
        return repository.getAllAnimeList()
    }
}