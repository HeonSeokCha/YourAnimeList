package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo.api.Input
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeCharaUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Input<Int>): Flow<NetWorkState<AnimeCharacterQuery.Data>> =
        flow {
            try {
                emit(NetWorkState.Loading())
                emit(NetWorkState.Success(repository.getAnimeCharacter(animeId).data!!))
            } catch (e: Exception) {
                emit(NetWorkState.Error(e.message.toString()))
            }
        }
}