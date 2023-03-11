package com.chs.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.presentation.AnimeRecommendQuery
import kotlinx.coroutines.flow.Flow

data class AnimeRecState(
    val animeRecInfo: Flow<PagingData<AnimeRecommendQuery.Edge>>? = null
)
