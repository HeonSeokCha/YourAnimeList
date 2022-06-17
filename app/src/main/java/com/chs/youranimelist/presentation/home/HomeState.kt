package com.chs.youranimelist.presentation.home

import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.fragment.AnimeList

data class HomeState(
    val pagerList: List<HomeRecommendListQuery.Medium?> = arrayListOf(),
    val nestedList: ArrayList<ArrayList<AnimeList>> = arrayListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)
