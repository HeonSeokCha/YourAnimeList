package com.chs.youranimelist.presentation.bottom.animeList

sealed interface AnimeListIntent {
    data class ClickAnime(val id: Int, val idMal: Int) : AnimeListIntent
}