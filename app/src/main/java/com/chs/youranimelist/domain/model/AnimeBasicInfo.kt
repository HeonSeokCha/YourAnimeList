package com.chs.youranimelist.domain.model

data class AnimeBasicInfo(
    val id: Int,
    val idMal: Int,
    val title: TitleInfo,
    val imageInfo: ImageInfo,
    val averageScore: Int,
    val favoriteScore: Int,
    val season: String,
    val format: String,
    val status: String
)
