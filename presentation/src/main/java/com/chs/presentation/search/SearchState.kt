package com.chs.presentation.search

import androidx.paging.PagingData
import com.chs.presentation.SearchAnimeQuery
import com.chs.presentation.SearchCharacterQuery
import com.chs.presentation.SearchMangaQuery
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchAnimeResultPaging: Flow<PagingData<SearchAnimeQuery.Medium>>? = null,
    val searchMangaResultPaging: Flow<PagingData<SearchMangaQuery.Medium>>? = null,
    val searchCharaResultPaging: Flow<PagingData<SearchCharacterQuery.Character>>? = null,
    val isLoading: Boolean = false
)
