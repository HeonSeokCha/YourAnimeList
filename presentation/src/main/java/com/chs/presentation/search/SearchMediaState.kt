package com.chs.presentation.search

import androidx.paging.PagingData
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

data class SearchMediaState(
    val searchAnimeResultPaging: Flow<PagingData<AnimeInfo>>? = null,
//    val searchMangaResultPaging: Flow<PagingData<SearchMangaQuery.Medium>>? = null,
    val searchCharaResultPaging: Flow<PagingData<CharacterInfo>>? = null,
    val isLoading: Boolean = false
)
