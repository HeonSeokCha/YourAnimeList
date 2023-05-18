package com.chs.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.presentation.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            var searchQuery: String by remember { mutableStateOf("") }
            var searchListQuery: String by remember { mutableStateOf("") }
            var searchHistoryList: List<String> by remember { mutableStateOf(emptyList()) }

            YourAnimeListTheme {
                Scaffold(
                    topBar = {
                        AppBar(
                            navController = navController,
                            searchHistoryList = searchHistoryList
                        ) {
                            searchQuery = it
                        }
                    },
                    bottomBar = {
                        BottomBar(navController) {
                            searchListQuery = ""
                        }
                    },
                ) {
                    MainNavHost(
                        navController = navController,
                        modifier = Modifier.padding(it),
                        searchListQuery = searchListQuery,
                        searchQuery = searchQuery,
                        onBack = {
                            searchQuery = ""
                        },
                        searchHistory = { historyList ->
                            Log.e("MainActivity", historyList.size.toString())
                            searchHistoryList = historyList
                        }
                    )
                }
            }
        }
    }
}



