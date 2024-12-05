package com.chs.presentation.browse.studio

sealed class StudioDetailEvent {
    data class ChangeSortOption(val value: Pair<String, String>) : StudioDetailEvent()
    data object GetStudioDetailInfo : StudioDetailEvent()

    data class AnimeClick(
        val id: Int,
        val idMal: Int
    ) : StudioDetailEvent()

    data object OnCloseClick : StudioDetailEvent()
}