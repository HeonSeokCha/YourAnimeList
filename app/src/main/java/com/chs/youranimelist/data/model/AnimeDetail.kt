package com.chs.youranimelist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetail(
    @SerialName("mal_id")
    val malId: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("opening_themes")
    val openingThemes: List<String> = listOf(),
    @SerialName("ending_themes")
    val endingThemes: List<String> = listOf()
)