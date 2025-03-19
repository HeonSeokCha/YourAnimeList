package com.chs.domain.usecase

import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.DataResult
import com.chs.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharaDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaId: Int): DataResult<CharacterDetailInfo, DataError.RemoteError> {
        return repository.getCharacterDetailInfo(charaId)
    }
}