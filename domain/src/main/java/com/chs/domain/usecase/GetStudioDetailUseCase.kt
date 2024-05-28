package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudioDetailUseCase @Inject constructor(
    private val repository: StudioRepository
) {
    suspend operator fun invoke(studioId: Int): Flow<Resource<StudioDetailInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getStudioDetailInfo(studioId)))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    }
}