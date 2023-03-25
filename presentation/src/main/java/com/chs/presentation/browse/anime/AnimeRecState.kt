package com.chs.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import kotlinx.coroutines.flow.Flow

data class AnimeRecState(
    val animeRecInfo: Flow<PagingData<AnimeInfo>>? = null
)
