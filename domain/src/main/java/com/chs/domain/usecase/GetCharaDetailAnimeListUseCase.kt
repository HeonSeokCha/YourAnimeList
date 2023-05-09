package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharaDetailAnimeListUseCase @Inject constructor(
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