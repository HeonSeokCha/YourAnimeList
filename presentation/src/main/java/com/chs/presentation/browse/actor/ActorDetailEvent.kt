package com.chs.presentation.browse.actor

sealed interface ActorDetailEvent {
    data class OnAnimeClick(
        val id: Int,
        val idMal: Int
    ) : ActorDetailEvent

    data class OnCharaClick(val id: Int) : ActorDetailEvent

    data object OnCloseClick : ActorDetailEvent

    data object GetActorDetailInfo : ActorDetailEvent
}