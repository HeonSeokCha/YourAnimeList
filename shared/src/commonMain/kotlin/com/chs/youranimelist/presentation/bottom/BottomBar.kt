package com.chs.youranimelist.presentation.bottom

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.youranimelist.presentation.main.BottomNavigation
import com.chs.youranimelist.presentation.ui.theme.Red200
import com.chs.youranimelist.presentation.ui.theme.Red500
import com.chs.youranimelist.presentation.ui.theme.Red700

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    if (BottomNavigation.entries.any { currentRoute?.hasRoute(it.route::class) == true }) {
        NavigationBar(containerColor = Red200) {
            BottomNavigation.entries.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    selected = currentRoute?.hasRoute(navItem.route::class) ?: false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Red700,
                        selectedTextColor = Red700,
                        unselectedIconColor = Red500,
                        unselectedTextColor = Red500,
                        indicatorColor = Red200
                    ), onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = null) },
                    label = { Text(text = navItem.label) }
                )
            }
        }
    }
}