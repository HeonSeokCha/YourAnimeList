package com.chs.youranimelist.presentation.animeList

import com.chs.youranimelist.data.model.AnimeDto

data class AnimeListState(
    val isLoading: Boolean = false,
    val animeList: List<AnimeDto> = emptyList()
)
