package com.chs.youranimelist.presentation.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.youranimelist.presentation.ui.theme.Red200
import com.chs.youranimelist.presentation.ui.theme.Red500
import com.chs.youranimelist.presentation.ui.theme.Red700

@Composable
fun BottomBar(
    navController: NavHostController,
    onNavigate: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    if (navBackStackEntry?.destination?.route?.contains(Screen.SortListScreen.route) == false
        && navBackStackEntry?.destination?.route != Screen.SearchScreen.route
    ) {
        val items = listOf(
            BottomNavScreen.HomeScreen,
            BottomNavScreen.AnimeListScreen,
            BottomNavScreen.CharaListScreen,
        )
        BottomNavigation(
            backgroundColor = Red200
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    selectedContentColor = Red700,
                    unselectedContentColor = Red500,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        onNavigate()
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