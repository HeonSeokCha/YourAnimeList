package com.chs.youranimelist.domain.model

interface BaseAnimeInfo {
    val id: Int
    val idMal: Int
    val title: String
    val imageUrl: String?
    val imagePlaceColor: String?
    val averageScore: Int
    val favourites: Int
}