package com.chs.presentation.animeList

import com.chs.domain.model.AnimeInfo

data class AnimeListState(
    val animeList: List<AnimeInfo> = emptyList(),
    val searchText: String? = null
)
