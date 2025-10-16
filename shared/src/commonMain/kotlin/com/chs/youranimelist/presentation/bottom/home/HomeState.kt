package com.chs.youranimelist.presentation.bottom.home

import com.chs.youranimelist.domain.model.AnimeRecommendList
import com.chs.youranimelist.domain.model.CategoryType

data class HomeState(
    val animeCategoryList: List<CategoryType> = CategoryType.entries.toList(),
    val animeRecommendList: AnimeRecommendList? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
