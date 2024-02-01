package com.chs.presentation.home

import com.chs.domain.model.AnimeRecommendList
import com.chs.presentation.UiConst

data class HomeState(
    val animeCategoryList: List<Pair<String, Triple<UiConst.SortType, Int, String?>>> = UiConst.animeCategorySortList,
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)
