package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.SortFilter
import com.chs.domain.model.TagInfo
import com.chs.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val sortFilter: SortFilter = SortFilter(),
    val optionYears: List<Pair<String, String>> = UiConst.yearSortList,
    val optionSort: List<Pair<String, String>> = UiConst.sortTypeList,
    val optionSeason: List<Pair<String, String>> = UiConst.seasonFilterList,
    val optionGenres: List<String> = emptyList(),
    val optionStatus: List<Pair<String, String>> = UiConst.mediaStatusSortList,
    val optionTags: List<Pair<String, String?>> = emptyList()
)