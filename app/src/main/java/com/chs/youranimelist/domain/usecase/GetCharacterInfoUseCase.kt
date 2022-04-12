package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterInfoUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(characterId: Int): Flow<NetworkState<CharacterQuery.Data>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.getCharacterDetail(characterId).data!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}