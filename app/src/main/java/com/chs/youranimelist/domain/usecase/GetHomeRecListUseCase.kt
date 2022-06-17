package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeRecListUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {
    operator fun invoke(): Flow<Resource<HomeRecommendListQuery.Data>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getHomeRecommendList().data))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}