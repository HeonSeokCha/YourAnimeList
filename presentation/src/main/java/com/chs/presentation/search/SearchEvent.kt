package com.chs.presentation.search

sealed interface SearchEvent {
    data class OnChangeSearchQuery(val query: String) : SearchEvent
    data class OnAnimeClick(
        val id: Int,
        val idMal: Int
    ) : SearchEvent

    data class OnCharaClick(val id: Int) : SearchEvent
    data class OnTabSelected(val idx: Int) : SearchEvent

    data object GetSearchAnimeResult : SearchEvent
    data object GetSearchCharaResult : SearchEvent
}