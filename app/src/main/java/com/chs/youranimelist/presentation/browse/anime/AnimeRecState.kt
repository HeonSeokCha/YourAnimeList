package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeRecommendQuery

data class AnimeRecState(
    val isLoading: Boolean = false,
    val animeRecInfo: AnimeRecommendQuery.Data? = null
)
