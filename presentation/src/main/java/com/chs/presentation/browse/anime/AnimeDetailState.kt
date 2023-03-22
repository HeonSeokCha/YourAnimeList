package com.chs.presentation.browse.anime

import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: com.chs.domain.model.AnimeThemeInfo? = null,
    val isSaveAnime: AnimeInfo? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

