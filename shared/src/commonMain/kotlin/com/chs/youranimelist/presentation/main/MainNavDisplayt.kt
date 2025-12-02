package com.chs.youranimelist.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultTransitionSpec
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.NavigationTransitionSpec
import com.chs.youranimelist.presentation.bottom.animeList.AnimeListScreenRoot
import com.chs.youranimelist.presentation.bottom.animeList.AnimeListViewModel
import com.chs.youranimelist.presentation.bottom.charaList.CharaListScreenRoot
import com.chs.youranimelist.presentation.bottom.charaList.CharacterListViewModel
import com.chs.youranimelist.presentation.bottom.home.HomeScreenRoot
import com.chs.youranimelist.presentation.bottom.home.HomeViewModel
import com.chs.youranimelist.presentation.defaultPredictivePopTransitionSpec2
import com.chs.youranimelist.presentation.search.SearchIntent
import com.chs.youranimelist.presentation.search.SearchViewModel
import com.chs.youranimelist.presentation.search.SearchScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedListScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainNavHost(
    backStack: SnapshotStateList<MainScreen>,
    modifier: Modifier = Modifier,
    searchQuery: String,
    browseInfo: (BrowseInfo) -> Unit
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        popTransitionSpec = defaultTransitionSpec(),
        predictivePopTransitionSpec = defaultPredictivePopTransitionSpec2(),
        entryProvider = entryProvider {
            entry<MainScreen.Home> {
                val viewModel: HomeViewModel = koinViewModel()

                HomeScreenRoot(
                    viewModel = viewModel,
                    onNavigateAnimeDetail = { id, idMal ->
                        browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = idMal))
                    },
                    onNavigateSort = { sortInfo ->
                        backStack.add(sortInfo)
                    }
                )
            }

            entry<MainScreen.AnimeList> {
                val viewModel: AnimeListViewModel = koinViewModel()
                AnimeListScreenRoot(
                    viewModel = viewModel,
                    onNavigateAnimeDetail = browseInfo
                )
            }

            entry<MainScreen.CharaList> {
                val viewModel: CharacterListViewModel = koinViewModel()
                CharaListScreenRoot(
                    viewModel = viewModel,
                    onNavigateCharaDetail = browseInfo
                )
            }

            entry<MainScreen.Search>(metadata = NavigationTransitionSpec) {
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
                        browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = idMal))
                    },
                    onCharaClick = { id ->
                        browseInfo(BrowseInfo(type = MediaType.CHARACTER, id = id))
                    }
                )
            }

            entry< MainScreen.SortList>(metadata = NavigationTransitionSpec) { key ->
                val viewmodel: SortedViewModel = koinViewModel {
                    parametersOf(key.filter)
                }

                SortedListScreenRoot(
                    viewModel = viewmodel,
                    onClickAnime = { id, idMal ->
                        browseInfo(BrowseInfo(type = MediaType.MEDIA, id = id, idMal = idMal))
                    }
                )
            }
        }
    )
}