package com.chs.presentation.browse.anime.detail

import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeThemeInfo
import javax.annotation.concurrent.Immutable

@Immutable
data class AnimeDetailState(
    val animeDetailInfo: AnimeDetailInfo? = null,
    val animeThemes: AnimeThemeInfo? = null,
    val isSave: Boolean = false,
    val isLoading: Boolean = false
)

