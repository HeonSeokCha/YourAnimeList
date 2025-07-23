package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.AnimeInfo

sealed class AnimeDetailEvent {
    data object Idle : AnimeDetailEvent()

    sealed class ClickButton {
        data class Anime(
            val id: Int,
            val idMal: Int
        ) : AnimeDetailEvent()

        data class Chara(val id: Int) : AnimeDetailEvent()
        data class Tag(val tag: String) : AnimeDetailEvent()
        data class Genre(val genre: String) : AnimeDetailEvent()
        data class SeasonYear(
            val season: String,
            val year: Int
        ) : AnimeDetailEvent()

        data class Studio(val id: Int) : AnimeDetailEvent()
        data class Trailer(val id: String) : AnimeDetailEvent()
        data class Link(val url: String) : AnimeDetailEvent()
        data object Close : AnimeDetailEvent()
    }

    data class InsertAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent()
    data class DeleteAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent()

    data class OnTabSelected(val idx: Int) : AnimeDetailEvent()
    data object OnError : AnimeDetailEvent()
}