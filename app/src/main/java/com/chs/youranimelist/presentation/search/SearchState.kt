package com.chs.youranimelist.presentation.search

import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.SearchMangaQuery

data class SearchState(
    val searchAnimeResult: ArrayList<SearchAnimeQuery.Medium?> = arrayListOf(),
    val searchMangaResult: ArrayList<SearchMangaQuery.Medium?> = arrayListOf(),
    val searchCharaResult: ArrayList<SearchCharacterQuery.Character?> = arrayListOf(),
    val isLoading: Boolean = false
)
