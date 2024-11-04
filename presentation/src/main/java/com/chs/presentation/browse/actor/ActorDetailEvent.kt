package com.chs.presentation.browse.actor

sealed class ActorDetailEvent {
    data object GetActorDetailInfo : ActorDetailEvent()
}