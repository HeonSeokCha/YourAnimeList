package com.chs.youranimelist.presentation.search

import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val selectedTabIdx: Int = 0,
    val searchAnimeResultPaging: Flow<PagingData<AnimeInfo>>? = null,
    val searchCharaResultPaging: Flow<PagingData<CharacterInfo>>? = null,
    val isAnimeLoading: Boolean = false,
    val isCharaLoading: Boolean = false
)
