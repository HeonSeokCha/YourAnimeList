package com.chs.youranimelist.data.remote.usecase

import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.GenreQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {

    suspend operator fun invoke(): Flow<NetworkState<GenreQuery.Data>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.getGenre().data!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}