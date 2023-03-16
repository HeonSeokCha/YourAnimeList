package com.chs.domain.model

data class CharacterDetailInfo(
    val characterInfo: CharacterInfo,
    val description: String,
    val animeList: List<AnimeInfo>
)
