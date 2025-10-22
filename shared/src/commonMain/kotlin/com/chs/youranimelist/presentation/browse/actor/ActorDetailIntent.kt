package com.chs.youranimelist.presentation.browse.actor

import com.chs.youranimelist.domain.model.SortType

sealed interface ActorDetailIntent {

    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : ActorDetailIntent

    data class ClickLink(val url: String) : ActorDetailIntent

    data class ClickChara(val id: Int) : ActorDetailIntent

    data class ClickTab(val idx: Int) : ActorDetailIntent

    data object ClickClose : ActorDetailIntent

    data class ChangeSortOption(val option: SortType) : ActorDetailIntent

    data object OnLoad : ActorDetailIntent
    data object OnLoadComplete : ActorDetailIntent
    data object OnAppendLoad : ActorDetailIntent
    data object OnAppendLoadComplete : ActorDetailIntent
    data object OnError : ActorDetailIntent
}