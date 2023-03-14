package com.chs.domain.model

data class Anime(
    val animeId: Int = 0,
    val idMal: Int = 0,
    val title: String = "",
    val format: String = "",
    val seasonYear: Int? = null,
    val episode: Int? = null,
    val coverImage: String? = null,
    val averageScore: Int? = null,
    val favorites: Int? = null,
    val studio: String? = null,
    val genre: List<String?>? = null,
)
