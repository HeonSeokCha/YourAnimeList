package com.chs.presentation.main

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data class SortListScreen(
        val year: Int = 0,
        val season: String? = null,
        val sortOption: String? = null,
        val genre: String? = null
    ) : Screen()

    @Serializable
    data object SearchScreen : Screen()
}
