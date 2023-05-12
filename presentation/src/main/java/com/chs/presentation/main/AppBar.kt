package com.chs.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.presentation.R
import com.chs.presentation.ui.theme.Red500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    onQueryChange: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        "${Screen.SortListScreen.route}/{sortOption}/{sortYear}/{sortSeason}" -> {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Red500,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "sort_screen_back")
                    }
                }
            )
        }

        Screen.SearchScreen.route -> {
            SearchAppBar {
                onQueryChange(it)
            }
        }

        else -> {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Red500,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    if (navBackStackEntry?.destination?.route == BottomNavScreen.HomeScreen.route) {
                        IconButton(onClick = {
                            navController.navigate(Screen.SearchScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.TwoTone.Search,
                                contentDescription = "home_screen_search"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                        }) {
                            Icon(
                                imageVector = Icons.TwoTone.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    onQueryChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    DockedSearchBar(
        query = text,
        onQueryChange = {
            text = it
            onQueryChange(text)
        },
        onSearch = { isSearchActive = false },
        active = isSearchActive,
        onActiveChange = { isSearchActive = it },
        placeholder = { Text("Hinted search text") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
    ) {

    }
}