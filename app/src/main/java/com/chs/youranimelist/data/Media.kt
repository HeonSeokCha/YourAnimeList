package com.chs.youranimelist.data

import com.chs.youranimelist.type.*

data class Media(
    val id: Int,
    val title: String = "",
    val format: String = "",
    val status: MediaStatus? = null,
    val startDate: String? = null,
    val season: MediaSeason? = null,
    val seasonYear: Int? = null,
    val episode: Int? = null,
    val duration: Int? = null,
    val coverImage: String? = null,
    val bannerImage: String? = null,
    val averageScore: Int? = null,
    val popularity: Int? = null,
    val created: String = "",
)
