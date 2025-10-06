package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.repository.ActorRepository
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