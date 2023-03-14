package com.chs.presentation.browse.anime

import com.chs.presentation.AnimeDetailQuery

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailQuery.Data? = null,
    val animeThemes: com.chs.domain.model.AnimeThemeInfo? = null,
    val isSaveAnime: AnimeDto? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

