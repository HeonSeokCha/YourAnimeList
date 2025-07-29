package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.StudioDetailInfo
import com.chs.youranimelist.domain.repository.StudioRepository

class GetStudioDetailUseCase(
    private val repository: StudioRepository
) {
    suspend operator fun invoke(studioId: Int): DataResult<StudioDetailInfo, DataError.RemoteError> {
        return repository.getStudioDetailInfo(studioId)
    }
}