package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.AnimeInfo

sealed interface AnimeDetailIntent {

    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : AnimeDetailIntent

    data class ClickChara(val id: Int) : AnimeDetailIntent
    data class CLickTag(val tag: String) : AnimeDetailIntent
    data class CLickGenre(val genre: String) : AnimeDetailIntent
    data class CLickSeasonYear(
        val season: String,
        val year: Int
    ) : AnimeDetailIntent

    data class CLickStudio(val id: Int) : AnimeDetailIntent
    data class CLickTrailer(val id: String) : AnimeDetailIntent
    data class CLickLink(val url: String) : AnimeDetailIntent
    data object ClickClose : AnimeDetailIntent
    data class ClickSaved(val info: AnimeInfo) : AnimeDetailIntent
    data class OnTabSelected(val idx: Int) : AnimeDetailIntent

    data object OnLoadRecList : AnimeDetailIntent
    data object OnLoadCompleteRecList : AnimeDetailIntent

    data object OnAppendLoadRecList : AnimeDetailIntent
    data object OnAppendLoadCompleteRecList : AnimeDetailIntent
    data object OnErrorRecList : AnimeDetailIntent
}