package com.chs.youranimelist.data.remote.usecase

import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import com.chs.youranimelist.home.HomeRecommendListQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeRecListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(): Flow<NetWorkState<HomeRecommendListQuery.Data>> = flow {
        try {
            emit(NetWorkState.Loading())
            emit(NetWorkState.Success(repository.getHomeRecommendList().data!!))
        } catch (e: Exception) {
            emit(NetWorkState.Error(e.message.toString()))
        }
    }
}