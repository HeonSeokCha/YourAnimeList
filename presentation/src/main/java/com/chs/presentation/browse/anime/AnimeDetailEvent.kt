package com.chs.presentation.browse.anime

import com.chs.domain.model.AnimeInfo

sealed class AnimeDetailEvent {
    data object GetAnimeDetailInfo : AnimeDetailEvent()
    data class InsertAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent()
    data class DeleteAnimeInfo(val info: AnimeInfo) : AnimeDetailEvent()
}