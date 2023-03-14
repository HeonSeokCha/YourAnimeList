package com.chs.domain.model

data class CharacterDetailInfo(
    val characterInfo: com.chs.domain.model.CharacterInfo,
    val description: String,
    val animeList: List<com.chs.domain.model.AnimeInfo>
)
