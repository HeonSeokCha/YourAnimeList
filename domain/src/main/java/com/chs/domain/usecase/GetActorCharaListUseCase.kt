package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorCharaListUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    operator fun invoke(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<CharacterInfo>> {
        return repository.getActorRelationCharaList(
            actorId = actorId,
            sortOptions = sortOptions
        )
    }
}