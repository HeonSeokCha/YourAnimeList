package com.chs.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.common.UiConst
import com.chs.presentation.ConvertDate
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.charaList.CharaListScreen
import com.chs.presentation.home.HomeScreen
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.sortList.SortedListScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchListQuery: String,
    searchQuery: String,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = BottomNavScreen.HomeScreen.route
    ) {
        composable(BottomNavScreen.HomeScreen.route) {
            HomeScreen(navigator = navController)
        }
        composable(BottomNavScreen.AnimeListScreen.route) {
            AnimeListScreen(searchListQuery)
        }
        composable(BottomNavScreen.CharaListScreen.route) {
            CharaListScreen(searchListQuery)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(searchQuery, onBack)
        }
        composable(
            route = "${Screen.SortListScreen.route}/{sortOption}/{sortYear}/{sortSeason}",
            arguments = listOf(
                navArgument("sortOption") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("sortYear") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument("sortSeason") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
//                navArgument("sortGenre") {
//                    nullable = true
//                    defaultValue = null
//                    type = NavType.StringType
//                }
            )
        ) { backStackEntry ->
            SortedListScreen(
                sortOption = backStackEntry.arguments?.getString("sortOption"),
                sortYear = backStackEntry.arguments?.getString("sortYear"),
                sortSeason = backStackEntry.arguments?.getString("sortSeason")
            )
        }
    }
}