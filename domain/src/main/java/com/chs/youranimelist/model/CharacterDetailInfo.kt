package com.chs.youranimelist.model

data class CharacterDetailInfo(
    val characterInfo: com.chs.youranimelist.model.CharacterInfo,
    val description: String,
    val animeList: List<com.chs.youranimelist.model.AnimeInfo>
)
