package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeOverviewQuery

data class AnimeOverViewState(
    val isLoading: Boolean = false,
    val animeOverViewInfo: AnimeOverviewQuery.Data? = null
)
