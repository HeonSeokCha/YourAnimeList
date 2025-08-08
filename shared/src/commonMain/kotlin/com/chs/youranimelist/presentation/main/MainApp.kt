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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.chsLog
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainApp(onBrowse: (BrowseInfo) -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    val navController: NavHostController = rememberNavController()
    var searchQuery: String by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()

    YourAnimeListTheme {
        Scaffold(
            topBar = {
                AppBar(
                    navController = navController,
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
                BottomBar(navController)
            },
        ) {
            MainNavHost(
                navController = navController,
                modifier = Modifier.padding(it),
                searchQuery = searchQuery,
                browseInfo = { onBrowse(it) }
            )
        }
    }
}