package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharaDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(charaId: Int): Flow<Resource<CharacterQuery.Data>> {
        return repository.getCharacterDetail(charaId)
    }
}