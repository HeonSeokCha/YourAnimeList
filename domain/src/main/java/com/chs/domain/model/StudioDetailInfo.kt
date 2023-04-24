package com.chs.domain.model

data class StudioDetailInfo(
    val id: Int,
    val name: String,
    val favorites: Int,
    val animeList: List<AnimeInfo>
)
