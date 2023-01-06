package com.chs.youranimelist.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.youranimelist.presentation.animeList.AnimeListScreen
import com.chs.youranimelist.presentation.charaList.CharaListScreen
import com.chs.youranimelist.presentation.home.HomeScreen
import com.chs.youranimelist.presentation.search.SearchScreen
import com.chs.youranimelist.presentation.sortList.SortedListScreen
import com.chs.youranimelist.util.Constant

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchListQuery: String,
    searchQuery: String
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
            SearchScreen(searchQuery)
        }
        composable("${Screen.SortListScreen.route}/{title}") { backStackEntry ->
            SortedListScreen(
                backStackEntry.arguments?.getString("title")
                    ?: Constant.TRENDING_NOW
            )
        }
    }

}