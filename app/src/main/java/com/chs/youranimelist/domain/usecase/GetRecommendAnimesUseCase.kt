package com.chs.youranimelist.domain.usecase

import com.chs.youranimelist.data.mapper.convertAnimeBasicInfo
import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.repository.AnimeRepository

class GetRecommendAnimesUseCase(
    private val repository: AnimeRepository
) {
    suspend operator fun invoke(): AnimeRecommendList {
        return repository.getAnimeRecommendList()
    }
}