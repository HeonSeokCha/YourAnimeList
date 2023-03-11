package com.chs.presentation.model

data class CharacterDetailInfo(
    val characterInfo: com.chs.presentation.model.CharacterInfo,
    val description: String,
    val animeList: List<com.chs.presentation.model.AnimeInfo>
)
