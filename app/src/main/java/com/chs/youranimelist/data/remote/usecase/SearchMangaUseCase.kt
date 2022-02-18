package com.chs.youranimelist.data.remote.usecase

import com.apollographql.apollo.api.Input
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.repository.SearchRepository
import com.chs.youranimelist.search.SearchAnimeQuery
import com.chs.youranimelist.search.SearchMangaQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        page: Input<Int>,
        search: Input<String>
    ): Flow<NetworkState<SearchMangaQuery.Page>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.searchManga(page, search).data!!.page!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}