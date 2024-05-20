package com.chs.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.presentation.fromRoute
import com.chs.presentation.ui.theme.Red200
import com.chs.presentation.ui.theme.Red500
import com.chs.presentation.ui.theme.Red700

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (navBackStackEntry?.fromRoute() !is Screen.SearchScreen
        && navBackStackEntry?.fromRoute() !is Screen.SortListScreen
    ) {
        val items = listOf(
            Screen.HomeScreen,
            Screen.AnimeListScreen,
            Screen.CharaListScreen
        )

        NavigationBar(
            containerColor = Red200
        ) {
            val currentDestination = navBackStackEntry?.fromRoute()
            items.forEach { destination ->
                when (destination) {
                    is Screen.HomeScreen -> {
                        NavigationBarItem(
                            selected = currentDestination is Screen.HomeScreen,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Red700,
                                selectedTextColor = Red700,
                                unselectedIconColor = Red500,
                                unselectedTextColor = Red500,
                                indicatorColor = Red200
                            ), onClick = {
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) },
                            label = { Text(text = "Home") }
                        )
                    }

                    is Screen.AnimeListScreen -> {
                        NavigationBarItem(
                            selected = currentDestination is Screen.AnimeListScreen,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Red700,
                                selectedTextColor = Red700,
                                unselectedIconColor = Red500,
                                unselectedTextColor = Red500,
                                indicatorColor = Red200
                            ), onClick = {
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(Icons.Default.LocalMovies, contentDescription = null) },
                            label = { Text(text = "Anime") }
                        )
                    }

                    is Screen.CharaListScreen -> {
                        NavigationBarItem(
                            selected = currentDestination is Screen.CharaListScreen,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Red700,
                                selectedTextColor = Red700,
                                unselectedIconColor = Red500,
                                unselectedTextColor = Red500,
                                indicatorColor = Red200
                            ), onClick = {
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(Icons.Default.TagFaces, contentDescription = null) },
                            label = { Text(text = "Character") }
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}