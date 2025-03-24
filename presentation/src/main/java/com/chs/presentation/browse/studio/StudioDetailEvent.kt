package com.chs.presentation.browse.studio

sealed class StudioDetailEvent {

    data object Idle : StudioDetailEvent()

    sealed class ClickBtn {
        data class SortOption(val value: Pair<String, String>) : StudioDetailEvent()
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : StudioDetailEvent()

        data object Close : StudioDetailEvent()
        data class TabIdx(val idx: Int) : StudioDetailEvent()
    }

    data object OnError : StudioDetailEvent()
}