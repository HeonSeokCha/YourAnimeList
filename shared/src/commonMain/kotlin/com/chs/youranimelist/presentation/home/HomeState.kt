package com.chs.youranimelist.presentation.home

import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.CategoryType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.main.Screen

data class HomeState(
    val animeCategoryList: List<CategoryType> = CategoryType.entries.toList(),
    val animeRecommendList: AnimeRecommendList? = null,
    val isRefresh: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
