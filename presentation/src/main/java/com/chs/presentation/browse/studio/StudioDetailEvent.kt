package com.chs.presentation.browse.studio

sealed interface StudioDetailEvent {
    data class ChangeSortOption(val value: Pair<String, String>) : StudioDetailEvent

    data class AnimeClick(
        val id: Int,
        val idMal: Int
    ) : StudioDetailEvent

    data object OnCloseClick : StudioDetailEvent

    data object OnError : StudioDetailEvent
}