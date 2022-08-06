package com.chs.youranimelist.presentation.sortList

import com.chs.youranimelist.NoSeasonQuery
import com.chs.youranimelist.fragment.AnimeList

data class SortState(
    val animeSortList: ArrayList<AnimeList> = arrayListOf(),
    val genreList: List<String?> = emptyList(),
    val isLoading: Boolean = false
)