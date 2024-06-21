package com.chs.domain.usecase

import com.chs.common.Resource
import com.chs.domain.model.CharacterDetailInfo
import com.chs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharaDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(charaId: Int): Flow<Resource<CharacterDetailInfo>> {
        return repository.getCharacterDetailInfo(charaId)
    }
}