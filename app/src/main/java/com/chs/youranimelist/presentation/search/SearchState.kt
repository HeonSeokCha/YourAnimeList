package com.chs.youranimelist.presentation.search

import androidx.paging.PagingData
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchAnimeResultPaging: Flow<PagingData<SearchAnimeQuery.Medium>>? = null,
    val searchMangaResultPaging: Flow<PagingData<SearchMangaQuery.Medium>>? = null,
    val searchCharaResultPaging: Flow<PagingData<SearchCharacterQuery.Character>>? = null,
    val isLoading: Boolean = false
)
