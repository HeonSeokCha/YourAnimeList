package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val genreList: List<String?> = emptyList(),
    val selectType: String? = null,
    val selectSeason: String? = null,
    val selectYear: Int? = null,
    val selectGenre: String? = null,
    val isLoading: Boolean = false
)