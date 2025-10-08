package com.chs.youranimelist.presentation.sortList

sealed interface SortEffect {
    data object ShowErrorSnackBar : SortEffect
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : SortEffect
}