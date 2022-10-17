package com.chs.youranimelist.domain.usecase


import androidx.paging.PagingData
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeRecUseCase @Inject constructor(
    private val repository: AnimeDetailRepository
) {
    operator fun invoke(
        animeId: Int,
    ): Flow<PagingData<AnimeRecommendQuery.Edge>> = repository.getAnimeRecList(animeId)
}