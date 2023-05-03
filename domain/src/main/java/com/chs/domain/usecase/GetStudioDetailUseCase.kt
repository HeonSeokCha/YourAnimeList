package com.chs.domain.usecase

import com.chs.domain.model.StudioDetailInfo
import com.chs.domain.repository.StudioRepository
import javax.inject.Inject

class GetStudioDetailUseCase @Inject constructor(
    private val repository: StudioRepository
) {
    suspend operator fun invoke(studioId: Int): StudioDetailInfo {
        return repository.getStudioDetailInfo(studioId)
    }
}