package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.domain.model.AnimeDetails

data class AnimeOverViewState(
    val isLoading: Boolean = false,
    val animeOverViewInfo: AnimeOverviewQuery.Data? = null,
    val animeOverThemeInfo: AnimeDetails? = null
)
