package com.chs.youranimelist.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.animeList.AnimeListScreen
import com.chs.youranimelist.presentation.animeList.AnimeListViewModel
import com.chs.youranimelist.presentation.charaList.CharaListScreen
import com.chs.youranimelist.presentation.charaList.CharacterListViewModel
import com.chs.youranimelist.presentation.home.HomeScreenRoot
import com.chs.youranimelist.presentation.home.HomeViewModel
import com.chs.youranimelist.presentation.search.SearchEvent
import com.chs.youranimelist.presentation.search.SearchViewModel
import com.chs.youranimelist.presentation.search.SearchScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedListScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchQuery: String,
    browseInfo: (BrowseInfo) -> Unit
) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<Screen.Home> {
            val viewModel: HomeViewModel = koinViewModel()

            HomeScreenRoot(
                viewModel = viewModel,
                onNavigateAnimeDetail = { id, idMal ->
                    browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = id))
                },
                onNavigateSort = { sortInfo ->
                    navController.navigate(sortInfo)
                }
            )
        }

        composable<Screen.AnimeList> {
            val viewModel: AnimeListViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            AnimeListScreen(state) { id, idMal ->
                browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = id))
            }
        }

        composable<Screen.CharaList> {
            val viewmodel: CharacterListViewModel = koinViewModel()
            val state by viewmodel.state.collectAsStateWithLifecycle()
            CharaListScreen(state) { id ->
                browseInfo(BrowseInfo(type = MediaType.CHARACTER, id = id))
            }
        }

        composable<Screen.Search> {
            val viewModel: SearchViewModel = koinViewModel()

            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filter { it.isNotEmpty() }
                    .collect { viewModel.onEvent(SearchEvent.OnChangeSearchQuery(it)) }
            }

            SearchScreenRoot(
                viewModel = viewModel,
                onAnimeClick = { id, idMal ->
                    browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = id))
                },
                onCharaClick = { id ->
                    browseInfo(BrowseInfo(type = MediaType.CHARACTER, id = id))
                }
            )
        }

        composable<Screen.SortList> {
            val viewmodel: SortedViewModel = koinViewModel()

            SortedListScreenRoot(
                viewModel = viewmodel,
                onClickAnime = { id, idMal ->
                    browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = id))
                }
            )
        }
    }
}