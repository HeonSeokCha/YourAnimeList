package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getAnimeGenreList()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}