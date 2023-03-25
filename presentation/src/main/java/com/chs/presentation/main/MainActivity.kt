package com.chs.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chs.presentation.ui.theme.*
import com.chs.presentation.SearchWidgetState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var searchQuery by remember { mutableStateOf("") }
            var searchListQuery by remember { mutableStateOf("") }
            var searchWidgetState by remember { mutableStateOf(SearchWidgetState.CLOSED) }

            YourAnimeListTheme {
                Scaffold(
                    topBar = {
                        AppBar(
                            navController,
                            searchWidgetState,
                            onSearchTriggered = {
                                searchWidgetState = SearchWidgetState.OPENED
                            }, onClosedClicked = {
                                searchWidgetState = SearchWidgetState.CLOSED
                            }, onSearchClicked = {
                                searchQuery = it
                            }, onTextChanged = {
                                searchListQuery = it
                            }
                        )
                    },
                    bottomBar = {
                        BottomBar(navController) {
                            searchQuery = ""
                            searchListQuery = ""
                            searchWidgetState = SearchWidgetState.CLOSED
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
                        }
                    )
                }
            }
        }
    }
}



