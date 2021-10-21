package com.chs.youranimelist.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetails(
    @SerialName("mal_id")
    val malId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("opening_themes")
    val openingThemes: List<String>?,
    @SerialName("ending_themes")
    val endingThemes: List<String>?
)