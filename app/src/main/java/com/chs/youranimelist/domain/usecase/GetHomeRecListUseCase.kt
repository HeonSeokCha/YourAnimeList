package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.home.HomeRecommendListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeRecListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(): Flow<NetworkState<HomeRecommendListQuery.Data>> = flow {
        try {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(repository.getHomeRecommendList().data!!))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.message.toString()))
        }
    }
}