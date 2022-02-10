package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo.api.Input
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: Input<Int>): Flow<NetWorkState<AnimeDetailQuery.Data>> = flow {
        try {
            emit(NetWorkState.Loading())
            emit(NetWorkState.Success(repository.getAnimeDetail(animeId).data!!))
        } catch (e: Exception) {
            emit(NetWorkState.Error(e.message.toString()))
        }
    }
}