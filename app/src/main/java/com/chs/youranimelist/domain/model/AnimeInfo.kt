package com.chs.youranimelist.domain.model

data class AnimeInfo(
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String?,
    val averageScore: Int,
    val seasonYear: Int,
    val status: String
)
