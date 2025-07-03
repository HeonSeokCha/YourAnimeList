package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow

class GetActorMediaListUseCase(
    private val repository: ActorRepository
) {
    operator fun invoke(
        actorId: Int,
        sortOptions: List<String>
    ): Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>> {
        return repository.getActorRelationAnimeList(
            actorId = actorId,
            sortOptions = sortOptions
        )
    }
}