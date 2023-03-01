package com.chs.youranimelist.domain.model

data class CharacterDetailInfo(
    val characterInfo: CharacterInfo,
    val description: String,
    val animeList: List<AnimeInfo>
)
