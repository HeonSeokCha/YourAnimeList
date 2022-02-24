package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo3.api.Input
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.SearchRepository
import com.chs.youranimelist.search.SearchCharacterQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchCharaUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Int,
        search: String
    ): Flow<NetworkState<SearchCharacterQuery.Page>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.searchCharacter(page, search).data!!.page!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}