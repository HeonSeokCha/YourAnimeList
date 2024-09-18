package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.TagInfo
import com.chs.presentation.UiConst
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val selectSort: Pair<String, String>? = null,
    val selectSeason: Pair<String, String>? = null,
    val selectYear: Int? = null,
    val selectGenre: List<String>? = null,
    val selectStatus: String? = null,
    val selectTags: List<String>? = null,
    val optionYears: List<Pair<String, String?>> = UiConst.yearSortList,
    val optionSort: List<Pair<String, String>> = UiConst.sortTypeList,
    val optionSeason: List<Pair<String, String?>> = UiConst.seasonFilterList,
    val optionGenres: List<String> = emptyList(),
    val optionStatus: List<Pair<String, String?>> = UiConst.mediaStatusSortList,
    val optionTags: List<TagInfo> = emptyList()
)