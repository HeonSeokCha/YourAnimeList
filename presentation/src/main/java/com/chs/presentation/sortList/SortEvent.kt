package com.chs.presentation.sortList

sealed class SortEvent {
    data object GetSortList : SortEvent()
    data class ChangeYearOption(val value: Int) : SortEvent()
    data class ChangeSeasonOption(val value: Pair<String, String>) : SortEvent()
    data class ChangeSortOption(val value: Pair<String, String>) : SortEvent()
    data class ChangeGenreOption(val value: String) : SortEvent()
}