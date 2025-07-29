package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.domain.model.CharacterDetailInfo
import com.chs.youranimelist.util.DataError
import com.chs.youranimelist.util.DataResult
import com.chs.youranimelist.domain.repository.CharacterRepository

class GetCharaDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaId: Int): DataResult<CharacterDetailInfo, DataError.RemoteError> {
        return repository.getCharacterDetailInfo(charaId)
    }
}