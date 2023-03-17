package com.chs.domain.usecase

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeDetailRecListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(animeId: Int): PagingData<AnimeInfo> {
        return repository.getAnimeDetailInfoRecommendList(animeId)
    }
}