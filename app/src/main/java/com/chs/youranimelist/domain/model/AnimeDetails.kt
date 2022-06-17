package com.chs.youranimelist.domain.model

data class AnimeDetails(
    val malId: Int = 0,
    val title: String = "",
    val openingThemes: List<String> = listOf(),
    val endingThemes: List<String> = listOf()
)
