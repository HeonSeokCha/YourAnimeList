package com.chs.presentation.browse.anime

import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeThemeInfo

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo? = null,
    val isSaveAnime: AnimeInfo? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

