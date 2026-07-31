package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chs.youranimelist.di.KoinModule
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.bottom.BottomBar
import com.chs.youranimelist.presentation.bottom.BottomTopLevelBackStack
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

@Composable
fun MainApp(onBrowse: (BrowseInfo) -> Unit) {
    KoinApplication(koinConfiguration<KoinModule>()) {
        val backStack = remember { BottomTopLevelBackStack(MainScreen.Home) }
        var currentSearchQuery by remember { mutableStateOf("") }

        LaunchedEffect(backStack.backStack.last()) {
            if (backStack.backStack.lastOrNull() == null
                || backStack.backStack.lastOrNull() == MainScreen.Search
            ) return@LaunchedEffect

            currentSearchQuery = ""
        }

        YourAnimeListTheme {
            Scaffold(
                topBar = {
                    AppBar(
                        backStack = backStack.backStack,
                        onSearch = { currentSearchQuery = it },
                        onNavigateSearch = { backStack.add(MainScreen.Search) },
                        onBack = { backStack.removeLast() }
                    )
                },
                bottomBar = {
                    BottomBar(
                        backStack = backStack.backStack,
                        onClick = { backStack.addTopLevel(it) }
                    )
                },
            ) {
                MainNavHost(
                    backStack = backStack,
                    modifier = Modifier.padding(it),
                    searchQuery = currentSearchQuery,
                    browseInfo = onBrowse
                )
            }
        }
    }
}