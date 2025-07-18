package com.chs.domain.usecase

import com.chs.domain.model.CharacterDetailInfo
import com.chs.common.DataError
import com.chs.common.DataResult
import com.chs.domain.repository.CharacterRepository

class GetCharaDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaId: Int): DataResult<CharacterDetailInfo, DataError.RemoteError> {
        return repository.getCharacterDetailInfo(charaId)
    }
}