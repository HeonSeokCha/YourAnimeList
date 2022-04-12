package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeRecUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(
        animeId: Int,
        page: Int
    ): Flow<NetworkState<AnimeRecommendQuery.Data>> =
        flow {
            try {
                emit(NetworkState.Loading())
                emit(NetworkState.Success(repository.getAnimeRecList(animeId, page).data!!))
            } catch(e: Exception) {
                emit(NetworkState.Error(e.message.toString()))
            }
        }
}