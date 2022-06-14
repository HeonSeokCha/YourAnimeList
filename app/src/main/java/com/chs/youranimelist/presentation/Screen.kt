package com.chs.youranimelist.presentation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object AnimeListScreen : Screen("anime_list_screen")
    object CharaListScreen : Screen("chara_list_screen")
}