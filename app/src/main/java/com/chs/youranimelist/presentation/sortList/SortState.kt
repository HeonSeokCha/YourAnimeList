package com.chs.youranimelist.presentation.sortList

import androidx.paging.PagingData
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.NoSeasonNoYearQuery
import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.fragment.AnimeList
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeListQuery.Medium>>? = null,
    val animeNoSeasonSortPaging: Flow<PagingData<NoSeasonQuery.Medium>>? = null,
    val animeNoSeasonNoYearSortPaging: Flow<PagingData<NoSeasonNoYearQuery.Medium>>? = null,
    val genreList: List<String?> = emptyList(),
    val isLoading: Boolean = false
)