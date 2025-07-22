package com.chs.youranimelist.presentation.sortList

import com.chs.domain.model.SortFilter

sealed interface SortEvent {
    data object GetSortList : SortEvent
    data class ChangeSortOption(val value: SortFilter) : SortEvent
    data class ClickAnime(val id: Int, val idMal: Int) : SortEvent
    data object OnChangeDialogState : SortEvent
    data object OnRefresh : SortEvent
}