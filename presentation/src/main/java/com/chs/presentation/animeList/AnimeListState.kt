package com.chs.presentation.animeList

data class AnimeListState(
    val isLoading: Boolean = false,
    val animeList: List<AnimeDto> = emptyList()
)
