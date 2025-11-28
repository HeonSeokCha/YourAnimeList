package com.chs.youranimelist.presentation.bottom

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.chs.youranimelist.presentation.main.BottomNavigation
import com.chs.youranimelist.presentation.main.MainScreen
import com.chs.youranimelist.presentation.ui.theme.Red200
import com.chs.youranimelist.presentation.ui.theme.Red500
import com.chs.youranimelist.presentation.ui.theme.Red700

@Composable
fun BottomBar(backStack: SnapshotStateList<MainScreen>) {

    if (BottomNavigation.entries.any { it.route == backStack.last() }) {
        NavigationBar(containerColor = Red200) {
            BottomNavigation.entries.forEach { navItem ->
                NavigationBarItem(
                    selected = navItem.route == backStack.last(),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Red700,
                        selectedTextColor = Red700,
                        unselectedIconColor = Red500,
                        unselectedTextColor = Red500,
                        indicatorColor = Red200
                    ), onClick = {
                        backStack.remove(navItem.route)
                        backStack.add(navItem.route)
                    },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = null) },
                    label = { Text(text = navItem.label) }
                )
            }
        }
    }
}