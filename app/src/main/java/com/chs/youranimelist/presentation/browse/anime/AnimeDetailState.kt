package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeDetailQuery

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailQuery.Data? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

