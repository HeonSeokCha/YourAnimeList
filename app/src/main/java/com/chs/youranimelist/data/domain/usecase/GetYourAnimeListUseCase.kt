package com.chs.youranimelist.data.domain.usecase

import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import com.chs.youranimelist.data.remote.NetWorkState
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetYourAnimeListUseCase @Inject constructor(
    private val repository: YourAnimeListRepository
) {
    operator fun invoke(): Flow<List<Anime>> {
        return repository.getAllAnimeList()
    }
}