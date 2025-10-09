package com.chs.youranimelist.presentation.search

sealed interface SearchEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : SearchEffect
    data class NavigateCharaDetail(val id: Int) : SearchEffect
    data object ShowErrorSnackBar : SearchEffect
}