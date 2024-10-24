package com.chs.presentation.sortList

import com.chs.presentation.UiConst

data class SortOptions(
    val optionYears: List<Pair<String, String>> = UiConst.yearSortList,
    val optionSort: List<Pair<String, String>> = UiConst.sortTypeList,
    val optionSeason: List<Pair<String, String>> = UiConst.seasonFilterList,
    val optionGenres: List<String> = emptyList(),
    val optionStatus: List<Pair<String, String>> = UiConst.mediaStatusSortList,
    val optionTags: List<Pair<String, String?>> = emptyList()
)