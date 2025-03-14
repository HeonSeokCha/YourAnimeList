package com.chs.domain.usecase

import com.chs.domain.model.DataError
import com.chs.domain.model.Result
import com.chs.domain.model.VoiceActorDetailInfo
import com.chs.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorDetailInfoUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): Result<VoiceActorDetailInfo, DataError.RemoteError> {
        return repository.getActorDetailInfo(actorId)
    }
}