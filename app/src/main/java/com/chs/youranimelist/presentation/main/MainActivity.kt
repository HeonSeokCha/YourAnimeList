package com.chs.youranimelist.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.R
import com.chs.youranimelist.presentation.animeList.AnimeListScreen
import com.chs.youranimelist.presentation.charaList.CharaListScreen
import com.chs.youranimelist.presentation.home.HomeScreen
import com.chs.youranimelist.presentation.search.SearchScreen
import com.chs.youranimelist.presentation.sortList.SortedListScreen
import com.chs.youranimelist.presentation.ui.theme.*
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SearchWidgetState
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
                        searchQuery = searchQuery
                    )
                }
            }
        }
    }
}



