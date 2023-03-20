package com.chs.data.model

import kotlinx.serialization.SerialName

@Serializable
data class JikanAnimeDataDto(
    @SerialName("data")
    val data: JikanAnimeThemes
)

@Serializable
data class JikanAnimeThemes(
    @SerialName("openings")
    val openingThemes: List<String> = listOf(),
    @SerialName("endings")
    val endingThemes: List<String> = listOf()
)