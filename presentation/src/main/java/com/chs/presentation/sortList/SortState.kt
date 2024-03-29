package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val genreList: List<String> = emptyList(),
    val selectSort: Pair<String, String>? = null,
    val selectSeason: Pair<String, String>? = null,
    val selectYear: Int? = null,
    val selectGenre: String? = null,
    val menuList: List<Pair<String, List<Pair<String, String>>>> = emptyList(),
    val selectMenuIdx: Int = 0
)