package com.chs.youranimelist.presentation.sortList

import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.StatusType

sealed interface SortIntent {
    data object OnLoad : SortIntent
    data object LoadComplete : SortIntent

    data object OnAppendLoad : SortIntent
    data object AppendLoadComplete : SortIntent

    data object ClickFilterDialog : SortIntent
    data object DismissFilterDialog : SortIntent
    data class ClickAnime(val id: Int, val idMal: Int) : SortIntent
    data class OnChangeYear(val year: Int?) : SortIntent
    data class OnChangeSeason(val season: SeasonType?) : SortIntent
    data class OnChangeSort(val sort: SortType) : SortIntent
    data class OnChangeStatus(val status: StatusType?) : SortIntent
    data class OnChangeGenres(val genres: List<String>?) : SortIntent
    data class OnChangeTags(val tags: List<String>?) : SortIntent
    data object ClickFilterApply : SortIntent
}