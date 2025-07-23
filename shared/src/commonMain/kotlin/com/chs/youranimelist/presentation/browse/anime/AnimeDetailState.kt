package com.chs.youranimelist.presentation.browse.anime

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import kotlinx.coroutines.flow.Flow

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo = AnimeThemeInfo(),
    val animeRecList: Flow<PagingData<AnimeInfo>>? = null,
    val selectTabIdx: Int = 0,
    val isSave: Boolean? = null
)

