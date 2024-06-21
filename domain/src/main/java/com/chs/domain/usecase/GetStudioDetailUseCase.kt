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
    operator fun invoke(studioId: Int): Flow<Resource<StudioDetailInfo>> {
        return repository.getStudioDetailInfo(studioId)
    }
}