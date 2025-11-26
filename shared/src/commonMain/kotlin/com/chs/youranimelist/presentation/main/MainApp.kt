package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.bottom.BottomBar
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainApp(onBrowse: (BrowseInfo) -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    var searchQuery: String by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val module = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Screen.Home::class, Screen.Home.serializer())
            subclass(Screen.SortList::class, Screen.SortList.serializer())
            subclass(Screen.Search::class, Screen.Search.serializer())
            subclass(Screen.AnimeList::class, Screen.AnimeList.serializer())
            subclass(Screen.CharaList::class, Screen.CharaList.serializer())
        }
    }

    val config = SavedStateConfiguration { serializersModule = module }
    val backStack = rememberNavBackStack(configuration = config, Screen.Home)

    YourAnimeListTheme {
        Scaffold(
            topBar = {
                AppBar(
                    backStack = backStack,
                    searchHistoryList = state,
                    onQueryChange = {
                        if (it.isNotEmpty()) {
                            viewModel.insertSearchHistory(it)
                        }
                        searchQuery = it
                    }, onDeleteSearchHistory = {
                        viewModel.deleteSearchHistory(it)
                    }
                )
            },
            bottomBar = {
                BottomBar(backStack = backStack)
            },
        ) {
            MainNavHost(
                backStack = backStack,
                modifier = Modifier.padding(it),
                searchQuery = searchQuery,
                browseInfo = onBrowse
            )
        }
    }
}