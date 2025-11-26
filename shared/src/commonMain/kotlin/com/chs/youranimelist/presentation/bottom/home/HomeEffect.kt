package com.chs.youranimelist.presentation.bottom.home

import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.domain.model.SortType

sealed interface HomeEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : HomeEffect
    data class NavigateSort(
        val year: Int? = null,
        val option: List<SortType> = listOf(),
        val season: SeasonType? = null
    ) : HomeEffect
}