package com.chs.youranimelist.presentation.animeList

import com.chs.youranimelist.domain.model.AnimeInfo

data class AnimeListState(
    val isLoading: Boolean = true,
    val list: List<AnimeInfo> = emptyList()
)