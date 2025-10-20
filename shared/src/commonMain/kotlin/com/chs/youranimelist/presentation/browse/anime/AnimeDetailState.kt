package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeThemeInfo

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo = AnimeThemeInfo(),
    val selectTabIdx: Int = 0,
    val isSave: Boolean = false,
    val isShowDialog: Boolean = false,
    val dialogText: String = "",
    val isDescExpand: Boolean = false,
    val animeRecListLoading: Boolean = false,
    val animeRecListAppendLoading: Boolean = false,
    val animeRecListError: Boolean = false
)

