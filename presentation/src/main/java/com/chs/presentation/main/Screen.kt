package com.chs.presentation.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed class Screen : Parcelable {
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
