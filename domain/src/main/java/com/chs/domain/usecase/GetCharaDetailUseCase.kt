package com.chs.domain.usecase

import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.model.DataError
import com.chs.domain.model.Result
import com.chs.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharaDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaId: Int): Result<CharacterDetailInfo, DataError.RemoteError> {
        return repository.getCharacterDetailInfo(charaId)
    }
}