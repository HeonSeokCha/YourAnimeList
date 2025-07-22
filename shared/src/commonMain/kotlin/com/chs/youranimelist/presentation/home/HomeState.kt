package com.chs.youranimelist.presentation.home

import com.chs.domain.model.AnimeRecommendList
import presentation.UiConst
import presentation.main.Screen

data class HomeState(
    val animeCategoryList: List<Pair<String, Screen.SortList>> = UiConst.animeCategorySortList,
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = true,
)
