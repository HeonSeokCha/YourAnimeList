package com.chs.youranimelist.presentation.home

import com.chs.youranimelist.model.AnimeRecommendList

data class HomeState(
    val animeRecommendList: com.chs.youranimelist.model.AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
