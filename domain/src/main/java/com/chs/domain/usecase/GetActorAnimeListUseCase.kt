package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorAnimeListUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    operator fun invoke(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<AnimeInfo>> {
        return repository.getActorRelationAnimeList(
            actorId = actorId,
            sortOptions = sortOptions
        )
    }
}