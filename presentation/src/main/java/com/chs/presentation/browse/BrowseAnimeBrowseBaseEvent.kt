package com.chs.presentation.browse

sealed interface BrowseAnimeBrowseBaseEvent : BrowseBaseEvent {
    data class OnAnimeClick(
        val id: Int,
        val idMal: Int
    ) : BrowseAnimeBrowseBaseEvent
}