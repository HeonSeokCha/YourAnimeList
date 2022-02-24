package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo3.api.Input
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeCharaUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): Flow<NetworkState<AnimeCharacterQuery.Data>> =
        flow {
            try {
                emit(NetworkState.Loading())
                emit(NetworkState.Success(repository.getAnimeCharacter(animeId).data!!))
            } catch (e: Exception) {
                emit(NetworkState.Error(e.message.toString()))
            }
        }
}