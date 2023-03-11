package com.chs.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.charaList.CharaListScreen
import com.chs.presentation.home.HomeScreen
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.sortList.SortedListScreen
import com.chs.presentation.util.Constant

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
            SearchScreen(
                searchQuery,
                onBack
            )
        }
        composable("${Screen.SortListScreen.route}/{title}") { backStackEntry ->
            SortedListScreen(
                backStackEntry.arguments?.getString("title")
                    ?: Constant.TRENDING_NOW
            )
        }
    }

}