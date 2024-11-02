package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorDetailInfoUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    operator fun invoke(actorId: Int): Flow<Resource<VoiceActorDetailInfo>> {
        return repository.getActorDetailInfo(actorId)
    }
}