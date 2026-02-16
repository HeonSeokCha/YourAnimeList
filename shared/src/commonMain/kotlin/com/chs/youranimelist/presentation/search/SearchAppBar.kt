package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

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
            .padding(horizontal = 16.dp),
        inputField = inputField,
        state = searchBarState
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