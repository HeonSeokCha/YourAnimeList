package com.chs.youranimelist.presentation.browse.actor

sealed class ActorDetailEvent {
    data object Idle : ActorDetailEvent()

    sealed class ClickBtn {
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : ActorDetailEvent()

        data class Link(val url: String) : ActorDetailEvent()

        data class Chara(val id: Int) : ActorDetailEvent()

        data class TabIdx(val idx: Int) : ActorDetailEvent()

        data object Close : ActorDetailEvent()
    }

    data class ChangeSortOption(val option: String) : ActorDetailEvent()

    data object OnError : ActorDetailEvent()
}