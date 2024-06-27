package com.chs.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.animeList.AnimeListViewModel
import com.chs.presentation.charaList.CharaListScreen
import com.chs.presentation.charaList.CharacterListViewModel
import com.chs.presentation.home.HomeScreen
import com.chs.presentation.home.HomeViewModel
import com.chs.presentation.search.SearchEvent
import com.chs.presentation.search.SearchMediaViewModel
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.sortList.SortedListScreen
import com.chs.presentation.sortList.SortedViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

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
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.HomeScreen)
            }
            val viewModel: HomeViewModel = hiltViewModel(parentEntry)
            HomeScreen(
                state = viewModel.state,
                event = viewModel::changeOption
            ) {
                navController.navigate(it)
            }
        }

        composable<Screen.AnimeListScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.AnimeListScreen)
            }
            val viewModel: AnimeListViewModel = hiltViewModel(parentEntry)
            AnimeListScreen(state = viewModel.state)
        }

        composable<Screen.CharaListScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CharaListScreen)
            }
            val viewmodel: CharacterListViewModel = hiltViewModel(parentEntry)
            CharaListScreen(state = viewmodel.state)
        }

        composable<Screen.SearchScreen> { it ->
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.SearchScreen)
            }

            val viewModel: SearchMediaViewModel = hiltViewModel(parentEntry)

            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filter { it.isNotEmpty() }
                    .collect {
                        viewModel.onEvent(SearchEvent.ChangeSearchQuery(it))
                    }
            }

            SearchScreen(
                state = viewModel.state,
                onBack = onBack,
                onEvent = viewModel::onEvent
            )
        }

        composable<Screen.SortListScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(it.toRoute<Screen.SortListScreen>())
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)

            SortedListScreen(
                state = viewmodel.state,
                onEvent = viewmodel::changeSortEvent
            )
        }
    }
}