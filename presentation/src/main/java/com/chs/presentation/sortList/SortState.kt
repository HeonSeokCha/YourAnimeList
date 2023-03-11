package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.presentation.NoSeasonNoYearQuery
import com.chs.presentation.NoSeasonQuery
import com.chs.presentation.fragment.AnimeList
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeList>>? = null,
    val animeNoSeasonSortPaging: Flow<PagingData<NoSeasonQuery.Medium>>? = null,
    val animeNoSeasonNoYearSortPaging: Flow<PagingData<NoSeasonNoYearQuery.Medium>>? = null,
    val genreList: List<String?> = emptyList(),
    val isLoading: Boolean = false
)