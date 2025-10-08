package com.chs.youranimelist.presentation.browse.studio

import com.chs.youranimelist.domain.model.SortType

sealed class StudioDetailEvent {

    data object Idle : StudioDetailEvent()

    sealed class ClickBtn {
        data class SortOption(val value: SortType) : StudioDetailEvent()
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : StudioDetailEvent()

        data object Close : StudioDetailEvent()
        data class TabIdx(val idx: Int) : StudioDetailEvent()
    }

    data object OnError : StudioDetailEvent()
}