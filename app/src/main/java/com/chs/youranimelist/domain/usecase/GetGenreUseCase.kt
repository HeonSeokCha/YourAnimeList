package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.GenreQuery
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val repository: AnimeListRepository
) {

    suspend operator fun invoke(): Flow<Resource<GenreQuery.Data>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getGenre().data))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}