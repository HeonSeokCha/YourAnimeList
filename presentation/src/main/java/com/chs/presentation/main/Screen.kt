package com.chs.presentation.main

sealed class Screen(
    val route: String
) {
    object SortListScreen : Screen("sortList_screen")
    object SearchScreen : Screen("search_screen")
}
