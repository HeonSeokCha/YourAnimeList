package com.chs.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.presentation.ui.theme.*
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                        searchQuery = searchQuery
                    )
                }
            }
        }
    }
}
