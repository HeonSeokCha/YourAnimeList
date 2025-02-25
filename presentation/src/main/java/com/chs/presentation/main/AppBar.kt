package com.chs.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.presentation.R
import com.chs.presentation.ui.theme.Red500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    searchHistoryList: List<String>,
    onQueryChange: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = navBackStackEntry?.destination

    if (currentDest?.hasRoute(Screen.SortList::class) == true) {
        TopAppBar(
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Red500,
                navigationIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "sort_screen_back")
                }
            }
        )
    } else if (currentDest?.hasRoute(Screen.Search::class) == true) {

        SearchAppBar(
            searchHistoryList = searchHistoryList,
            onSearch = {
                onQueryChange(it)
            }, onDeleteSearchHistory = {
                onDeleteSearchHistory(it)
            }
        )
    } else {
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
                IconButton(onClick = {
                    navController.navigate(Screen.Search)
                }) {
                    Icon(
                        imageVector = Icons.TwoTone.Search,
                        contentDescription = "home_screen_search"
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchAppBar(
    onSearch: (String) -> Unit,
    searchHistoryList: List<String>,
    onDeleteSearchHistory: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    isSearchActive = false
                    onSearch(it)
                },
                expanded = isSearchActive,
                onExpandedChange = { isSearchActive = it },
                placeholder = { Text("Search here...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            isSearchActive = false
                        }
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        expanded = isSearchActive,
        onExpandedChange = { isSearchActive = it }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchHistoryList) { title ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 14.dp)
                    .combinedClickable(
                        onClick = {
                            text = title
                            isSearchActive = false
                            onSearch(title)
                        },
                        onLongClick = {
                            text = title
                            isShowDialog = true
                        }
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = null
                    )
                    Text(text = title)
                }
            }
        }

        if (isShowDialog) {
            AlertDialog(
                onDismissRequest = { isShowDialog = false },
                title = { Text(text = text) },
                text = { Text(text = "Are You Sure Delete Search History?") },
                confirmButton = {
                    Button(
                        onClick = {
                            isShowDialog = false
                            onDeleteSearchHistory(text)
                        }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isShowDialog = false
                        }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}