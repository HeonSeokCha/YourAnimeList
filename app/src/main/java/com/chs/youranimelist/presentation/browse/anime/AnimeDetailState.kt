package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.domain.model.Anime
import com.chs.youranimelist.domain.model.AnimeDetails

data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailQuery.Data? = null,
    val animeThemes: AnimeDetails? = null,
    val isSaveAnime: AnimeDto? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

