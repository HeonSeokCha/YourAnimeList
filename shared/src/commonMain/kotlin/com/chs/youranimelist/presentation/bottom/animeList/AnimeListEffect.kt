package com.chs.youranimelist.presentation.bottom.animeList

sealed interface AnimeListEffect {
    data class NavigateAnimeDetail(
        val id: Int,
        val idMal: Int
    ) : AnimeListEffect
}