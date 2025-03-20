package com.chs.presentation.home

import com.chs.domain.model.AnimeRecommendList
import com.chs.presentation.UiConst
import com.chs.presentation.main.Screen

data class HomeState(
    val animeCategoryList: List<Pair<String, Screen.SortList>> = UiConst.animeCategorySortList,
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = true,
)
