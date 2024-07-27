package com.chs.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector
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

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object AnimeListScreen : Screen()

    @Serializable
    data object CharaListScreen : Screen()
}

enum class BottomNavigation(
    val label: String,
    val icon: ImageVector,
    val route: Screen
) {
    HOME("Home", Icons.Filled.Home, Screen.HomeScreen),
    ANIME("Collection", Icons.Filled.LocalMovies, Screen.AnimeListScreen),
    CHARA("Collection", Icons.Filled.SupervisorAccount, Screen.CharaListScreen),
}