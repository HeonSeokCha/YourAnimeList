package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.domain.model.AnimeThemeInfo

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailQuery.Data? = null,
    val animeThemes: AnimeThemeInfo? = null,
    val isSaveAnime: AnimeDto? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

