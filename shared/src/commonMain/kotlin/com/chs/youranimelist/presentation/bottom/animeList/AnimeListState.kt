package com.chs.youranimelist.presentation.bottom.animeList

import com.chs.youranimelist.domain.model.AnimeSavedInfo

data class AnimeListState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val list: List<AnimeSavedInfo> = emptyList()
)