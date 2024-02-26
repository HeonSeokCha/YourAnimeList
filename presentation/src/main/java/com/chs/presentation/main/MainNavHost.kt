package com.chs.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.presentation.UiConst
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.charaList.CharaListScreen
import com.chs.presentation.home.HomeScreen
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.sortList.SortedListScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchQuery: String,
    onBack: () -> Unit,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = BottomNavScreen.HomeScreen.route
    ) {
        composable(BottomNavScreen.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(BottomNavScreen.AnimeListScreen.route) {
            AnimeListScreen()
        }

        composable(BottomNavScreen.CharaListScreen.route) {
            CharaListScreen()
        }

        composable(Screen.SearchScreen.route) {
            SearchScreen(
                searchQuery = searchQuery,
                onBack = onBack,
            )
        }

        composable(
            route = "${Screen.SortListScreen.route}/{sort}/{year}/{season}",
            arguments = listOf(
                navArgument(UiConst.KEY_SORT) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument(UiConst.KEY_YEAR) {
                    type = NavType.IntType
                },
                navArgument(UiConst.KEY_SEASON) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            SortedListScreen()
        }
    }
}