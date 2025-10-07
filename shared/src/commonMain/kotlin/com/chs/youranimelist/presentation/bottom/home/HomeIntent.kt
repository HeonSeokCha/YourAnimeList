package com.chs.youranimelist.presentation.bottom.home

import com.chs.youranimelist.domain.model.CategoryType

sealed interface HomeIntent {
    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : HomeIntent

    data class ClickCategory(val categoryType: CategoryType) : HomeIntent
}