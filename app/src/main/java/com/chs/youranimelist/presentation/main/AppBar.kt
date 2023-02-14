package com.chs.youranimelist.presentation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.youranimelist.R
import com.chs.youranimelist.presentation.ui.theme.Pink80
import com.chs.youranimelist.util.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Pink80,
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
                            Text(
                                text = stringResource(R.string.app_name),
                                color = Color.White
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Pink80,
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Pink80
            ),
            value = textState,
            onValueChange = {
                textState = it
                onValueChanged(textState)
            },
            placeholder = {
                Text(
                    text = "Search here...",
                    color = Color.White,
                    fontWeight = FontWeight.Thin
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
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