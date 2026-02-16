package com.chs.youranimelist.presentation.search

data class SearchState(
    val selectedTabIdx: Int = 0,
    val isAnimeLoading: Boolean = false,
    val isCharaLoading: Boolean = false,
    val searchHistory: List<String> = emptyList()
)
