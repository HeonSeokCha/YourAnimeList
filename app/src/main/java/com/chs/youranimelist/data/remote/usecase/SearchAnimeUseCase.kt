package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo.api.Input
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.SearchRepository
import com.chs.youranimelist.search.SearchAnimeQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Input<Int>,
        query: Input<String>
    ): Flow<NetworkState<SearchAnimeQuery.Page>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.searchAnime(page, query).data!!.page!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}