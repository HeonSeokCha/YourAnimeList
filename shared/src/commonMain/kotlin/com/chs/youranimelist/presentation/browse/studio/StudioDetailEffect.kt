package com.chs.youranimelist.presentation.browse.studio

sealed interface StudioDetailEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : StudioDetailEffect
    data object OnClose : StudioDetailEffect
}