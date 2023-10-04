package com.chs.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JikanAnimeDataDto(
    @SerialName("data")
    val data: JikanAnimeThemes
)

@Serializable
data class JikanAnimeThemes(
    @SerialName("openings")
    val openingThemes: List<String> = emptyList(),
    @SerialName("endings")
    val endingThemes: List<String> = emptyList()
)