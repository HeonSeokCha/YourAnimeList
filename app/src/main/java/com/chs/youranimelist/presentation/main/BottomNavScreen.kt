package com.chs.youranimelist.presentation.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.ui.graphics.vector.ImageVector
import com.chs.youranimelist.R

sealed class BottomNavScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    object HomeScreen : BottomNavScreen("home_screen", Icons.Default.Home, R.string.home)
    object AnimeListScreen :
        BottomNavScreen("animeList_screen", Icons.Default.LocalMovies, R.string.anime_list)

    object CharaListScreen :
        BottomNavScreen("charaList_screen", Icons.Default.TagFaces, R.string.chara_list)
}
