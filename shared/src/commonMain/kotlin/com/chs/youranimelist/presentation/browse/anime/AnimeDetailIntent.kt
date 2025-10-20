package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.AnimeInfo

sealed interface AnimeDetailIntent {

    data class ClickAnime(
        val id: Int,
        val idMal: Int
    ) : AnimeDetailIntent

    data class ClickChara(val id: Int) : AnimeDetailIntent
    data class ClickTag(val tag: String) : AnimeDetailIntent
    data class ClickGenre(val genre: String) : AnimeDetailIntent
    data class ClickSeasonYear(
        val season: String,
        val year: Int
    ) : AnimeDetailIntent

    data class ClickStudio(val id: Int) : AnimeDetailIntent
    data class ClickTrailer(val id: String) : AnimeDetailIntent
    data class ClickLink(val url: String) : AnimeDetailIntent
    data object ClickClose : AnimeDetailIntent
    data class ClickSaved(val info: AnimeInfo) : AnimeDetailIntent
    data class LongClickTag(val tag: String) : AnimeDetailIntent
    data class ClickSpoiler(val desc: String) : AnimeDetailIntent
    data object ClickExpand : AnimeDetailIntent
    data object ClickDialogConfirm : AnimeDetailIntent
    data class OnTabSelected(val idx: Int) : AnimeDetailIntent

    data object OnLoadRecList : AnimeDetailIntent
    data object OnLoadCompleteRecList : AnimeDetailIntent

    data object OnAppendLoadRecList : AnimeDetailIntent
    data object OnAppendLoadCompleteRecList : AnimeDetailIntent
    data object OnErrorRecList : AnimeDetailIntent
}