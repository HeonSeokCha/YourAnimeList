package com.chs.youranimelist.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector
import com.chs.youranimelist.domain.model.SortFilter
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainScreen {
    @Serializable
    data class SortList(val filter: SortFilter) : MainScreen

    @Serializable
    data object Search : MainScreen

    @Serializable
    data object Home : MainScreen

    @Serializable
    data object AnimeList : MainScreen

    @Serializable
    data object CharaList : MainScreen
}

enum class BottomNavigation(
    val label: String,
    val icon: ImageVector,
    val route: MainScreen
) {
    HOME("Home", Icons.Filled.Home, MainScreen.Home),
    ANIME("Animation", Icons.Filled.LocalMovies, MainScreen.AnimeList),
    CHARA("Character", Icons.Filled.SupervisorAccount, MainScreen.CharaList),
}