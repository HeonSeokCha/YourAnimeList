package com.chs.presentation.sortList

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val genreList: List<String?> = emptyList(),
    val isLoading: Boolean = false
)