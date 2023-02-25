package com.chs.youranimelist.domain.model

data class AnimeInfo(
    val id: Int,
    val idMal: Int,
    val title: TitleInfo,
    val imageInfo: ImageInfo,
    val averageScore: Int,
    val favoriteScore: Int,
    val season: String,
    val seasonYear: Int,
    val format: String,
    val status: String
)
