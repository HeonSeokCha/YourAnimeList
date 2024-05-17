package com.chs.presentation.main

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.chs.presentation.ui.theme.Red200
import com.chs.presentation.ui.theme.Red500
import com.chs.presentation.ui.theme.Red700

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (navBackStackEntry?.toRoute<Screen.SearchScreen>() is Screen.SearchScreen
        && navBackStackEntry?.toRoute<Screen.SortListScreen>() is Screen.SortListScreen
    ) {
        val items = listOf(
            BottomNavScreen.HomeScreen,
            BottomNavScreen.AnimeListScreen,
            BottomNavScreen.CharaListScreen,
        )
        NavigationBar(
            containerColor = Red200,
        ) {
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Red700,
                        selectedTextColor = Red700,
                        unselectedIconColor = Red500,
                        unselectedTextColor = Red500,
                        indicatorColor = Red200
                    ), onClick = {
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