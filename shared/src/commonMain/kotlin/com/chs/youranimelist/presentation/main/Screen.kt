package com.chs.youranimelist.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.chs.youranimelist.domain.model.SortFilter
import com.chs.youranimelist.presentation.sortList.SortOptions
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen : NavKey {
    @Serializable
    data class SortList(val filter: SortFilter) : Screen()

    @Serializable
    data object Search : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object AnimeList : Screen()

    @Serializable
    data object CharaList : Screen()
}

enum class BottomNavigation(
    val label: String,
    val icon: ImageVector,
    val route: Screen
) {
    HOME("Home", Icons.Filled.Home, Screen.Home),
    ANIME("Animation", Icons.Filled.LocalMovies, Screen.AnimeList),
    CHARA("Character", Icons.Filled.SupervisorAccount, Screen.CharaList),
}