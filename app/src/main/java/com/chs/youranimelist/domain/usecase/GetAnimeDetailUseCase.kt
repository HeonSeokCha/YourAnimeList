package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Int): Flow<NetworkState<AnimeDetailQuery.Data>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.getAnimeDetail(animeId).data!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}