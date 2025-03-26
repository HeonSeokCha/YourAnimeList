package com.chs.presentation.main

import android.app.appsearch.SearchResults
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.presentation.R
import com.chs.presentation.ui.theme.Red200
import com.chs.presentation.ui.theme.Red500
import kotlinx.coroutines.launch

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
            }, onBack = {
                navController.navigateUp()
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
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Search)
                    }
                ) {
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
    onBack: () -> Unit,
    searchHistoryList: List<String>,
    onDeleteSearchHistory: (String) -> Unit
) {
    var isShowDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                shape = RectangleShape,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = {
                    scope.launch {
                        searchBarState.animateToCollapsed()
                    }
                    onSearch(it)
                },
                placeholder = { Text("Search here...") },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            if (searchBarState.currentValue == SearchBarValue.Expanded) {
                                scope.launch { searchBarState.animateToCollapsed() }
                            } else onBack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = SearchBarDefaults.inputFieldColors(
                    focusedContainerColor = Red200,
                    unfocusedContainerColor = Red500,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.White,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White
                )
            )
        }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopAppBarDefaults.MediumAppBarCollapsedHeight),
        shape = RectangleShape,
        state = searchBarState,
        inputField = inputField
    )

    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = inputField,
        colors = SearchBarDefaults.colors(
            containerColor = Red200,
            inputFieldColors = TextFieldDefaults.colors()
        )
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(searchHistoryList) { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 14.dp)
                        .combinedClickable(
                            onClick = {
                                textFieldState.edit {
                                    delete(0, length)
                                    append(title)
                                }
                                scope.launch { searchBarState.animateToCollapsed() }
                                onSearch(title)
                            },
                            onLongClick = {
                                textFieldState.edit {
                                    delete(0, length)
                                    append(title)
                                }
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
    }

    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = { isShowDialog = false },
            title = { Text(text = textFieldState.text.toString()) },
            text = { Text(text = "Are You Sure Delete Search History?") },
            confirmButton = {
                Button(
                    onClick = {
                        isShowDialog = false
                        onDeleteSearchHistory(
                            textFieldState.text.toString()
                        )
                    }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { isShowDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}