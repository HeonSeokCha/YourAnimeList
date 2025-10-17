package com.chs.youranimelist.presentation.browse.anime

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.AnimeThemeInfo
import kotlinx.coroutines.flow.Flow

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo = AnimeThemeInfo(),
    val selectTabIdx: Int = 0,
    val isSave: Boolean = false,
    val animeRecListLoading: Boolean = false,
    val animeRecListAppendLoading: Boolean = false,
    val animeRecListisError: Boolean = false
)

