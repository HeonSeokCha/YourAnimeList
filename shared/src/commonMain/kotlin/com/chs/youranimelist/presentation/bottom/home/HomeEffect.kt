package com.chs.youranimelist.presentation.bottom.home

sealed interface HomeEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : HomeEffect
    data class NavigateSort(
        val year: Int? = null,
        val option: List<String> = listOf(),
        val season: String? = null
    ) : HomeEffect
}