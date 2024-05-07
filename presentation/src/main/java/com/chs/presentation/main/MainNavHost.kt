package com.chs.presentation.main

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.presentation.UiConst
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.animeList.AnimeListViewModel
import com.chs.presentation.charaList.CharaListScreen
import com.chs.presentation.charaList.CharacterListViewModel
import com.chs.presentation.home.HomeScreen
import com.chs.presentation.home.HomeState
import com.chs.presentation.home.HomeViewModel
import com.chs.presentation.search.SearchMediaViewModel
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.sortList.SortedListScreen
import com.chs.presentation.sortList.SortedViewModel

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
            val parentEntry = remember(it) {
                navController.getBackStackEntry(BottomNavScreen.HomeScreen.route)
            }
            val viewModel: HomeViewModel = hiltViewModel(parentEntry)
            HomeScreen(
                state = viewModel.state,
                navController = navController
            )
        }


        composable(BottomNavScreen.AnimeListScreen.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(BottomNavScreen.AnimeListScreen.route)
            }
            val viewModel: AnimeListViewModel = hiltViewModel(parentEntry)
            AnimeListScreen(
                state = viewModel.state
            )
        }

        composable(BottomNavScreen.CharaListScreen.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(BottomNavScreen.CharaListScreen.route)
            }
            val viewmodel: CharacterListViewModel = hiltViewModel(parentEntry)
            CharaListScreen(state = viewmodel.state)
        }

        composable(Screen.SearchScreen.route) {
            SearchScreen(
                searchQuery = searchQuery,
                onBack = onBack
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
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.SortListScreen.route)
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)

            SortedListScreen(
                state = viewmodel.state,
                onChangeOption = viewmodel::changeSortEvent
            )
        }
    }
}