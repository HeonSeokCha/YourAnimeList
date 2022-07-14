package com.chs.youranimelist.presentation.browse.anime

import com.chs.youranimelist.AnimeCharacterQuery

data class AnimeCharaState(
    val isLoading: Boolean = false,
    val isError: String = "",
    val animeCharaInfo: AnimeCharacterQuery.Data? = null
)
