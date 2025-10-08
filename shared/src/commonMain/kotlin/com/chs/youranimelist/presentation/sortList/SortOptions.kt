package com.chs.youranimelist.presentation.sortList

import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.StatusType
import com.chs.youranimelist.presentation.UiConst

data class SortOptions(
    val optionYears: List<Pair<String, String>> = UiConst.yearSortList,
    val optionSort: List<SortType> = SortType.entries.toList(),
    val optionSeason: List<SeasonType> = SeasonType.entries.toList(),
    val optionStatus: List<StatusType> = StatusType.entries.toList(),
    val optionTags: List<Pair<String, String?>> = emptyList(),
    val optionGenres: List<String> = emptyList()
)