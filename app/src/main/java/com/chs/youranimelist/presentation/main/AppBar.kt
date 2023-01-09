package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.youranimelist.R
import com.chs.youranimelist.util.SearchWidgetState

@Composable
fun AppBar(
    navController: NavHostController,
    searchWidgetState: SearchWidgetState,
    onSearchClicked: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onClosedClicked: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        "${Screen.SortListScreen.route}/{title}" -> {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "sort_screen_back")
                    }
                }
            )
        }
        Screen.SearchScreen.route -> {
            SearchAppBar(
                onSearchClicked = {
                    onSearchClicked(it)
                },
                onClosedClicked = {
                    onClosedClicked()
                },
                onValueChanged = {
                    onTextChanged(it)
                }
            )
        }
        else -> {
            when (searchWidgetState) {
                SearchWidgetState.OPENED -> {
                    SearchAppBar(
                        onSearchClicked = {
                            onSearchClicked(it)
                        },
                        onClosedClicked = {
                            onClosedClicked()
                        },
                        onValueChanged = {
                            onTextChanged(it)
                        }
                    )
                }
                SearchWidgetState.CLOSED -> {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.app_name))
                        },
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
                                    onSearchTriggered()
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
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    onSearchClicked: (String) -> Unit,
    onValueChanged: (String) -> Unit,
    onClosedClicked: () -> Unit,
) {
    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textState,
            onValueChange = {
                textState = it
                onValueChanged(textState)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        if (textState.isNotEmpty()) {
                            onSearchClicked("")
                            textState = ""
                        } else {
                            onClosedClicked()
                            keyboardController?.hide()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(textState)
                    keyboardController?.hide()
                }
            )
        )
    }
}