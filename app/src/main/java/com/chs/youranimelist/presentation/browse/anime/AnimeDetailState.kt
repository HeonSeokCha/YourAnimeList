package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.domain.model.Anime

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailQuery.Data? = null,
    val isSaveAnime: Anime? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

