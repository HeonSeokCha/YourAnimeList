package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeOverViewUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): Flow<NetworkState<AnimeOverviewQuery.Data>> =
        flow {
            try {
                emit(NetworkState.Loading())
                emit(NetworkState.Success(repository.getAnimeOverview(animeId).data!!))
            } catch (e: Exception) {
                emit(NetworkState.Error(e.message.toString()))
            }
        }
}