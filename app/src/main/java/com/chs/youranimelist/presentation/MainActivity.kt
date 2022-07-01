package com.chs.youranimelist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.R
import com.chs.youranimelist.presentation.destinations.*
import com.chs.youranimelist.ui.theme.YourAnimeListTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
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
                    DestinationsNavHost(
                        modifier = Modifier
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            ),
                        navController = navController,
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}

@Composable
fun AppBar(navController: NavHostController) {
    when (navController.currentDestinationAsState().value?.route) {
        HomeScreenDestination.route, AnimeListScreenDestination.route, CharaListScreenDestination.route -> {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    if (navController.currentDestinationAsState().value?.route == HomeScreenDestination.route) {
                        IconButton(onClick = {
                            navController.navigate(SearchScreenDestination.route)
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
        SortedListScreenDestination.route -> {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
        SearchScreenDestination.route -> {
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
    if (navController.currentDestinationAsState().value?.route != SortedListScreenDestination.route
        && navController.currentDestinationAsState().value?.route != SearchScreenDestination.route
    ) {
        BottomNavigation {
            BottomBarDestination.values().forEach { destination ->
                val isCurrentDestOnBackStack =
                    navController.isRouteOnBackStack(destination.direction)
                BottomNavigationItem(
                    selected = isCurrentDestOnBackStack,
                    onClick = {
                        if (isCurrentDestOnBackStack) {
                            navController.popBackStack(destination.direction, false)
                            return@BottomNavigationItem
                        }

                        navController.navigate(destination.direction) {
                            popUpTo(NavGraphs.root) {
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

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, Icons.Default.Home, R.string.home),
    AnimeList(AnimeListScreenDestination, Icons.Default.LocalMovies, R.string.anime_list),
    CharaList(CharaListScreenDestination, Icons.Default.TagFaces, R.string.chara_list),
}