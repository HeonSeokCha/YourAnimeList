package com.chs.youranimelist.domain.model

data class AnimeInfo(
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String?,
    val imagePlaceColor: String?,
    val averageScore: Int,
    val favourites: Int,
    val season: String,
    val seasonYear: Int,
    val format: String,
    val status: String,
)
