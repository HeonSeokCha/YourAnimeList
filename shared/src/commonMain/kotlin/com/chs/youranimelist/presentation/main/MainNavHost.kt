package com.chs.youranimelist.presentation.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.bottom.animeList.AnimeListScreenRoot
import com.chs.youranimelist.presentation.bottom.animeList.AnimeListViewModel
import com.chs.youranimelist.presentation.bottom.charaList.CharaListScreenRoot
import com.chs.youranimelist.presentation.bottom.charaList.CharacterListViewModel
import com.chs.youranimelist.presentation.bottom.home.HomeScreenRoot
import com.chs.youranimelist.presentation.bottom.home.HomeViewModel
import com.chs.youranimelist.presentation.search.SearchIntent
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
        enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) },
        exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) }
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
            AnimeListScreenRoot(
                viewModel = viewModel,
                onNavigateAnimeDetail = browseInfo
            )
        }

        composable<Screen.CharaList> {
            val viewModel: CharacterListViewModel = koinViewModel()
            CharaListScreenRoot(
                viewModel = viewModel,
                onNavigateCharaDetail = browseInfo
            )
        }

        composable<Screen.Search> {
            val viewModel: SearchViewModel = koinViewModel()

            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filter { it.isNotEmpty() }
                    .collect { viewModel.handleIntent(SearchIntent.OnChangeSearchQuery(it)) }
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