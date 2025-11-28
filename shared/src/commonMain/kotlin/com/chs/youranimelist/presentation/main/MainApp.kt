package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.bottom.BottomBar
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainApp(onBrowse: (BrowseInfo) -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    var searchQuery: String by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val backStack: SnapshotStateList<MainScreen> = remember { mutableStateListOf(MainScreen.Home) }

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