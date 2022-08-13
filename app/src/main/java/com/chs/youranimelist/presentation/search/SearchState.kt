package com.chs.youranimelist.presentation.search

import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery

data class SearchState(
    val searchAnimeResult: List<SearchAnimeQuery.Medium?> = listOf(),
    val searchMangaResult: List<SearchMangaQuery.Medium?> = listOf(),
    val searchCharaResult: List<SearchCharacterQuery.Character?> = listOf(),
    val isLoading: Boolean = false
)
