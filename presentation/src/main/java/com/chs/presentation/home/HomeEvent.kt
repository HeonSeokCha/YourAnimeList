package com.chs.presentation.home

sealed class HomeEvent {
    data object GetHomeData : HomeEvent()

    data class NavigateSort(
        val year: Int?,
        val option: List<String>,
        val season: String?
    ) : HomeEvent()

    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : HomeEvent()
}