package com.chs.youranimelist.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.youranimelist.AnimeRecommendQuery
import kotlinx.coroutines.flow.Flow

data class AnimeRecState(
    val animeRecInfo: Flow<PagingData<AnimeRecommendQuery.Edge>>? = null
)
