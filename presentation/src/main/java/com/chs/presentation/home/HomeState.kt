package com.chs.presentation.home

import com.chs.domain.model.AnimeRecommendList

data class HomeState(
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)
