package com.chs.youranimelist.presentation.home

import com.chs.youranimelist.domain.model.AnimeRecommendList

data class HomeState(
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
