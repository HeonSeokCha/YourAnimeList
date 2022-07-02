package com.chs.youranimelist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.R
import com.chs.youranimelist.presentation.home.HomeScreen
import com.chs.youranimelist.ui.theme.YourAnimeListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourAnimeListTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        AppBar(navController)
                    },
                    bottomBar = {
                        BottomBar(navController)
                    },
                ) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(it),
                        startDestination = BottomNavScreen.HomeScreen.route
                    ) {
                        composable(route = BottomNavScreen.HomeScreen.route) {
                            HomeScreen(navigator = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(navController: NavHostController) {
    when (navController.currentDestination?.route) {
        BottomNavScreen.HomeScreen.route, BottomNavScreen.AnimeListScreen.route, BottomNavScreen.CharaListScreen.route -> {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    if (navController.currentDestination?.route == BottomNavScreen.HomeScreen.route) {
                        IconButton(onClick = {
                            navController.navigate(Screen.SearchScreen.route)
                        }) {
                            Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
                            // search room db
                        }) {
                            Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
                        }
                    }
                }
            )
        }
        Screen.SortListScreen.route -> {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
        Screen.SearchScreen.route -> {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    if (navController.currentDestination?.route == Screen.SortListScreen.route
        && navController.currentDestination?.route != Screen.SearchScreen.route
    ) {
        val items = listOf(
            BottomNavScreen.HomeScreen,
            BottomNavScreen.AnimeListScreen,
            BottomNavScreen.CharaListScreen,
        )
        BottomNavigation {
            items.forEach { destination ->
                BottomNavigationItem(
                    selected = false,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    onClick = {
                        if (destination.route != navController.currentDestination?.route) {
                            navController.navigate(destination.route) {
                                popUpTo(0)
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = stringResource(destination.label)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.label),
                        )
                    }
                )
            }
        }
    }
}