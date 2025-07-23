package com.chs.youranimelist.presentation.home

import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.main.Screen

data class HomeState(
    val animeCategoryList: List<Pair<String, Screen.SortList>> = UiConst.animeCategorySortList,
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = true,
)
