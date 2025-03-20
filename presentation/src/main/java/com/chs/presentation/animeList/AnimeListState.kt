package com.chs.presentation.animeList

import com.chs.domain.model.AnimeInfo

data class AnimeListState(
    val isLoading: Boolean = true,
    val list: List<AnimeInfo> = emptyList()
)