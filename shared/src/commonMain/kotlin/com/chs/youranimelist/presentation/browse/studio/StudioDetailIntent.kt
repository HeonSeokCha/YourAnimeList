package com.chs.youranimelist.presentation.browse.studio

import com.chs.youranimelist.domain.model.SortType

sealed interface StudioDetailIntent {
    data class ClickSortOption(val value: SortType) : StudioDetailIntent
    data object ClickClose : StudioDetailIntent

    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : StudioDetailIntent

    data object OnLoading : StudioDetailIntent
    data object OnLoadComplete : StudioDetailIntent
    data object OnAppendLoading : StudioDetailIntent
    data object OnAppendLoadComplete : StudioDetailIntent
}