package com.chs.youranimelist.presentation.browse

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme

@Composable
fun BrowseApp() {
    val navController = rememberNavController()
    YourAnimeListTheme {
//        BrowseNavHost(
//            navController = navController,
//            intent = intent
//        )
    }
}