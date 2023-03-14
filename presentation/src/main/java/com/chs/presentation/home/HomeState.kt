package com.chs.presentation.home

data class HomeState(
    val animeRecommendList: com.chs.domain.model.AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
