package com.chs.youranimelist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailDto(
    @SerialName("data")
    val data: AnimeThemes
)

@Serializable
data class AnimeThemes(
    @SerialName("openings")
    val openingThemes: List<String> = listOf(),
    @SerialName("endings")
    val endingThemes: List<String> = listOf()
)