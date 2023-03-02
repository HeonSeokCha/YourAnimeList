package com.chs.youranimelist.data.model

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
    val openingThemes: List<String> = listOf(),
    @SerialName("endings")
    val endingThemes: List<String> = listOf()
)