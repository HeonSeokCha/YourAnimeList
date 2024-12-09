package com.chs.presentation.main

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.chs.presentation.home.HomeScreenRoot
import com.chs.presentation.home.HomeViewModel
import com.chs.presentation.search.SearchViewModel
import com.chs.presentation.search.SearchScreen
import com.chs.presentation.search.SearchScreenRoot
import com.chs.presentation.sortList.SortedListScreen
import com.chs.presentation.sortList.SortedViewModel

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
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()

            HomeScreenRoot(
                viewModel = viewModel,
                onAnimeClick = { id, idMal ->
                    context.startActivity(
                        Intent(
                            context,
                            BrowseActivity::class.java
                        ).apply {
                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                            this.putExtra(UiConst.TARGET_ID, id)
                            this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                        }
                    )
                },
                onSortScreenClick = {
                    navController.navigate(it)
                }
            )
        }

        composable<Screen.AnimeList> {
            val viewModel: AnimeListViewModel = hiltViewModel()
            val list by viewModel.state.collectAsStateWithLifecycle()
            AnimeListScreen(list = list) { id, idMal ->
                context.startActivity(
                    Intent(
                        context,
                        BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                        this.putExtra(UiConst.TARGET_ID, id)
                        this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                    }
                )
            }
        }

        composable<Screen.CharaList> {
            val viewmodel: CharacterListViewModel = hiltViewModel()
            val list by viewmodel.state.collectAsStateWithLifecycle()
            CharaListScreen(list = list) { id ->
                context.startActivity(
                    Intent(
                        context,
                        BrowseActivity::class.java
                    ).apply {
                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                        this.putExtra(UiConst.TARGET_ID, id)
                    }
                )
            }
        }

        composable<Screen.Search> {
            val viewModel: SearchViewModel = hiltViewModel()

            SearchScreenRoot(
                viewModel = viewModel,
                onAnimeClick = { id, idMal ->
                    Intent(
                        context,
                        BrowseActivity::class.java
                    ).apply {
                        putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                        putExtra(UiConst.TARGET_ID, id)
                        putExtra(UiConst.TARGET_ID_MAL, idMal)
                    }
                },
                onCharaClick = { id ->
                    Intent(
                        context,
                        BrowseActivity::class.java
                    ).apply {
                        putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                        putExtra(UiConst.TARGET_ID, id)
                    }
                },
                onBackClick = { navController.navigateUp() }
            )
        }

        composable<Screen.SortList> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(it.toRoute<Screen.SortList>())
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)
            val state by viewmodel.state.collectAsStateWithLifecycle()

            SortedListScreen(
                state = state,
                onEvent = viewmodel::changeSortEvent
            ) { id, idMal ->
                context.startActivity(
                    Intent(
                        context,
                        BrowseActivity::class.java
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