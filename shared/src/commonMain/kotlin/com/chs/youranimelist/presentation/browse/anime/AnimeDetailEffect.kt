package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.domain.model.SeasonType

sealed interface AnimeDetailEffect {
    data class NavigateAnimeDetail(val id: Int, val idMal: Int) : AnimeDetailEffect
    data class NavigateCharaDetail(val id: Int) : AnimeDetailEffect
    data class NavigateYouTube(val id: String) : AnimeDetailEffect
    data class NavigateSortGenre(val genre: String) : AnimeDetailEffect
    data class NavigateSortTag(val tag: String) : AnimeDetailEffect
    data class NavigateSortSeasonYear(val seasonType: SeasonType, val year: Int) : AnimeDetailEffect
    data class NavigateStudio(val id: Int) : AnimeDetailEffect
    data class NavigateBrowser(val url: String) : AnimeDetailEffect
    data object NavigateClose : AnimeDetailEffect
}