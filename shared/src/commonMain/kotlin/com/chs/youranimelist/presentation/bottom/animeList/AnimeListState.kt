package com.chs.youranimelist.presentation.bottom.animeList

import com.chs.youranimelist.domain.model.AnimeInfo

data class AnimeListState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val list: List<AnimeInfo> = emptyList()
)