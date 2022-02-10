package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo.api.Input
import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterInfoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(characterId: Input<Int>): Flow<NetWorkState<CharacterQuery.Data>> = flow {
        try {
            emit(NetWorkState.Loading())
            emit(NetWorkState.Success(repository.getCharacterDetail(characterId).data!!))
        } catch (e: Exception) {
            emit(NetWorkState.Error(e.message.toString()))
        }
    }
}