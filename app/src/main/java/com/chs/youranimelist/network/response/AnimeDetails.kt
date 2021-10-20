package com.chs.youranimelist.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetails(
    val malId: Int,
    val title: String,
    val openingThemes: List<String>?,
    val endingThemes: List<String>?
)