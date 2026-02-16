package com.chs.youranimelist.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.chs.youranimelist.presentation.ui.theme.Red500
import com.chs.youranimelist.res.Res
import com.chs.youranimelist.res.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(backStack: SnapshotStateList<MainScreen>) {
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

        is MainScreen.Search -> Unit

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