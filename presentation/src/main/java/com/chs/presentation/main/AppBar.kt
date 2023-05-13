package com.chs.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.presentation.R
import com.chs.presentation.ui.theme.Red500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    searchHistoryList: List<String>,
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
            SearchAppBar(
                searchHistoryList = searchHistoryList,
                onSearch = {
                    onQueryChange(it)
                }
            )
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
    onSearch: (String) -> Unit,
    searchHistoryList: List<String>
) {
    var text by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .padding(top = 8.dp),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            isSearchActive = false
            onSearch(it)
        },
        active = isSearchActive,
        onActiveChange = { isSearchActive = it },
        placeholder = { Text("Search here...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = {
                text = ""
                isSearchActive = false
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchHistoryList) { title ->
                Text(text = title)
            }
        }
    }
}