package com.chs.youranimelist.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.presentation.ui.theme.Red500
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    onSearch: (String) -> Unit,
    onBack: () -> Unit,
) {
    val viewModel = koinViewModel<SearchBarViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isShowDialog by remember { mutableStateOf(false) }
    var isActive by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {
        TopAppBar(
            title = {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { isActive = it.isFocused },
                    state = textFieldState,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    onKeyboardAction = {
                        val query = textFieldState.text.toString()
                        if (query.isNotEmpty()) {
                            viewModel.insertSearchHistory(query)
                            onSearch(query)
                            isActive = false
                            focusManager.clearFocus()
                        }
                    },
                    decorator = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (textFieldState.text.isEmpty()) {
                                Text(
                                    text = "Search...",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(Color.White)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Red500,
                actionIconContentColor = Color.White,
                navigationIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = isActive && state.searchHistory.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.searchHistory) { title ->
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
                                    viewModel.insertSearchHistory(title)
                                    onSearch(title)
                                    isActive = false
                                    focusManager.clearFocus()
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
                        viewModel.deleteSearchHistory(text)
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