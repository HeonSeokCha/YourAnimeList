package com.chs.presentation.sortList

import com.chs.domain.model.SortFilter

sealed class SortEvent {
    data object GetSortList : SortEvent()
    data class ChangeSortOption(val value: SortFilter) : SortEvent()
}