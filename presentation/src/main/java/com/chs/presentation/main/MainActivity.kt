package com.chs.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.presentation.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
