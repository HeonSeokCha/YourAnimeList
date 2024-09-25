package com.chs.presentation.main

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.presentation.UiConst
import com.chs.presentation.animeList.AnimeListScreen
import com.chs.presentation.animeList.AnimeListViewModel
import com.chs.presentation.browse.BrowseActivity
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

    val context = LocalContext.current

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                state = viewModel.state,
                event = viewModel::changeOption,
                onNavigate = {
                    navController.navigate(it)
                }
            ) { id, idMal ->
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                        this.putExtra(UiConst.TARGET_ID, id)
                        this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                    }
                )
            }
        }

        composable<Screen.AnimeListScreen> {
            val viewModel: AnimeListViewModel = hiltViewModel()
            val list by viewModel.state.collectAsStateWithLifecycle()
            AnimeListScreen(list = list) { id, idMal ->
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                        this.putExtra(UiConst.TARGET_ID, id)
                        this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                    }
                )
            }
        }

        composable<Screen.CharaListScreen> {
            val viewmodel: CharacterListViewModel = hiltViewModel()
            val list by viewmodel.state.collectAsStateWithLifecycle()
            CharaListScreen(list = list) { id ->
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                        this.putExtra(UiConst.TARGET_ID, id)
                    }
                )
            }
        }

        composable<Screen.SearchScreen> {
            val viewModel: SearchMediaViewModel = hiltViewModel()

            SearchScreen(
                state = viewModel.state,
                searchQuery = searchQuery,
                onBack = onBack,
                onEvent = viewModel::onEvent
            ) { type, id, idMal ->
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        putExtra(UiConst.TARGET_TYPE, type)
                        putExtra(UiConst.TARGET_ID, id)

                        if (idMal != null) {
                            putExtra(UiConst.TARGET_ID_MAL, idMal)
                        }
                    }
                )
            }
        }

        composable<Screen.SortListScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(it.toRoute<Screen.SortListScreen>())
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)

            SortedListScreen(
                state = viewmodel.state,
                onEvent = viewmodel::changeSortEvent
            ) { id, idMal ->
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                        this.putExtra(UiConst.TARGET_ID, id)
                        this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                    }
                )
            }
        }
    }
}