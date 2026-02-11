package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.ui.theme.Red500
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.app_name
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    backStack: SnapshotStateList<MainScreen>,
    searchHistoryList: List<String>,
    onQueryChange: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit
) {
    when (backStack.last()) {
        is MainScreen.SortList -> {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Red500,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "sort_screen_back")
                    }
                }
            )
        }

        is MainScreen.Search -> {
            SearchAppBar(
                searchHistoryList = searchHistoryList,
                onSearch = {
                    onQueryChange(it)
                }, onDeleteSearchHistory = {
                    onDeleteSearchHistory(it)
                }
            )
        }

        else -> {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.app_name),
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
                            onQueryChange("")
                            backStack.add(MainScreen.Search)
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    onSearch: (String) -> Unit,
    searchHistoryList: List<String>,
    onDeleteSearchHistory: (String) -> Unit
) {
    var isShowDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()
    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = {
                    val query = textFieldState.text.toString()
                    scope.launch { searchBarState.animateToCollapsed() }
                    onSearch(query)
                },
                placeholder = {
                    Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search...")
                },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            )
        }

    SearchBar(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .statusBarsPadding(),
        inputField = inputField,
        state = searchBarState,
    )

    ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchHistoryList) { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 14.dp)
                        .combinedClickable(
                            enabled = true,
                            onClick = {
                                textFieldState.clearText()
                                textFieldState.edit { append(title) }
                                scope.launch { searchBarState.animateToCollapsed() }
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
                Button(onClick = { isShowDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
