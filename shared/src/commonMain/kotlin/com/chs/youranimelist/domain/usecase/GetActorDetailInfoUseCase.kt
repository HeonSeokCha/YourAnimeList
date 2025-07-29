package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.model.VoiceActorDetailInfo
import com.chs.youranimelist.domain.repository.ActorRepository

class GetActorDetailInfoUseCase(
    private val repository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): DataResult<VoiceActorDetailInfo, DataError.RemoteError> {
        return repository.getActorDetailInfo(actorId)
    }
}