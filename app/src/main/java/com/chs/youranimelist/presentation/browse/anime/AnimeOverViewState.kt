package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.AnimeDetails

data class AnimeOverViewState(
    val isLoading: Boolean = false,
    val animeOverThemeInfo: AnimeDetails? = null
)
