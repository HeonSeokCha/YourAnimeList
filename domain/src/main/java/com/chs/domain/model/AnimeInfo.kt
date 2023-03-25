package com.chs.domain.model

data class AnimeInfo(
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String?,
    val imagePlaceColor: String?,
    val averageScore: Int,
    val favourites: Int,
    val seasonYear: Int,
    val format: String,
    val status: String
)
