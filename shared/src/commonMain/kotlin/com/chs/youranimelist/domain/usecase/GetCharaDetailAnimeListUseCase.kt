package com.chs.youranimelist.domain.usecase

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetCharaDetailAnimeListUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(
        charaId: Int,
        sort: String
    ): Flow<PagingData<AnimeInfo>> {
        return repository.getCharacterDetailAnimeList(
            charaId,
            sort
        )
    }
}