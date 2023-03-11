package com.chs.presentation.home

data class HomeState(
    val animeRecommendList: com.chs.presentation.model.AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
