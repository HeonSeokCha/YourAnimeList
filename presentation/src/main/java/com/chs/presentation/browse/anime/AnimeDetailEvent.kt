package com.chs.presentation.browse.anime

import com.chs.domain.model.AnimeInfo

sealed interface AnimeDetailEvent {
    data object OnRefresh : AnimeDetailEvent
    data class OnAnimeClick(
        val id: Int,
        val idMal: Int
    ) : AnimeDetailEvent

    data class OnCharaClick(val id: Int) : AnimeDetailEvent
    data class OnTagClick(val tag: String) : AnimeDetailEvent
    data class OnGenreClick(val genre: String) : AnimeDetailEvent
    data class OnSeasonYearClick(
        val season: String,
        val year: Int
    ) : AnimeDetailEvent

    data class OnStudioClick(val id: Int) : AnimeDetailEvent

    data class InsertAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent
    data class DeleteAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent

    data class OnTabSelected(val idx: Int) : AnimeDetailEvent

    data class OnTrailerClick(val id: String) : AnimeDetailEvent
    data object OnCloseClick : AnimeDetailEvent
}