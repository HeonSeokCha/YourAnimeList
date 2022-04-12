package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.search.SearchAnimeQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Int,
        search: String
    ): Flow<NetworkState<SearchAnimeQuery.Page>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.searchAnime(page, search).data!!.page!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}