package com.chs.youranimelist.presentation.sortList

import app.cash.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SortFilter
import kotlinx.coroutines.flow.Flow

data class SortState(
    val animeSortPaging: Flow<PagingData<AnimeInfo>>? = null,
    val sortFilter: SortFilter = SortFilter(),
    val sortOptions: SortOptions = SortOptions(),
    val isRefresh: Boolean = false,
    val isShowDialog: Boolean = false,
    val isLoading: Boolean = true
)