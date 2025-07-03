package com.chs.domain.usecase

import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository

class GetStudioDetailUseCase(
    private val repository: StudioRepository
) {
    suspend operator fun invoke(studioId: Int): DataResult<StudioDetailInfo, DataError.RemoteError> {
        return repository.getStudioDetailInfo(studioId)
    }
}