package com.chs.youranimelist.presentation.browse.actor

sealed interface ActorDetailEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : ActorDetailEffect
    data class NavigateCharaDetail(val id: Int) : ActorDetailEffect
    data class NavigateBrowser(val url: String) : ActorDetailEffect
    data object NavigateClose : ActorDetailEffect
}