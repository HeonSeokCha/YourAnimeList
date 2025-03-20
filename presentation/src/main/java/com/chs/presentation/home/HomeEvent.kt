package com.chs.presentation.home

sealed class HomeEvent {
    data object Idle : HomeEvent()
    sealed class Navigate : HomeEvent() {
        data class Sort(
            val year: Int?,
            val option: List<String>,
            val season: String?
        ) : Navigate()

        data class Anime(
            val id: Int,
            val idMal: Int
        ) : Navigate()
    }

    data class ShowToast(val message: String) : HomeEvent()
}