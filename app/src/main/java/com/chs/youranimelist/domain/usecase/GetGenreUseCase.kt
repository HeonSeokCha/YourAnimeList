package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.sortedlist.GenreQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {

    suspend operator fun invoke(): Flow<NetworkState<GenreQuery.Data>> = flow {
        repository.getGenre().catch { e ->
            emit(NetworkState.Error(e.message.toString()))
        }.collect {
            emit(NetworkState.Success(it.data!!))
        }
    }
}