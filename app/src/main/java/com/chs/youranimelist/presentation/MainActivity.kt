package com.chs.youranimelist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.R
import com.chs.youranimelist.presentation.animeList.AnimeListScreen
import com.chs.youranimelist.presentation.charaList.CharaListScreen
import com.chs.youranimelist.presentation.home.HomeScreen
import com.chs.youranimelist.presentation.sortList.SortedListScreen
import com.chs.youranimelist.ui.theme.YourAnimeListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            YourAnimeListTheme {
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
                        composable(BottomNavScreen.HomeScreen.route) { HomeScreen(navigator = navController) }
                        composable(BottomNavScreen.AnimeListScreen.route) { AnimeListScreen() }
                        composable(BottomNavScreen.CharaListScreen.route) { CharaListScreen() }
                        composable(Screen.SearchScreen.route) { CharaListScreen() }
                        composable(Screen.SortListScreen.route) { SortedListScreen("") }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
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
        else -> {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    if (navBackStackEntry?.destination?.route == BottomNavScreen.HomeScreen.route) {
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
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    if (navBackStackEntry?.destination?.route != Screen.SortListScreen.route
        && navBackStackEntry?.destination?.route != Screen.SearchScreen.route
    ) {
        val items = listOf(
            BottomNavScreen.HomeScreen,
            BottomNavScreen.AnimeListScreen,
            BottomNavScreen.CharaListScreen,
        )
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
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