package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): Flow<Resource<AnimeDetailInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                Resource.Success(repository.getAnimeDetailInfo(animeId))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        }
    }
}