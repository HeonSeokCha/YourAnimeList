package com.chs.presentation.browse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.chs.presentation.ui.theme.YourAnimeListTheme

class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            YourAnimeListTheme {
                BrowseNavHost(
                    navController = navController,
                    intent = intent
                )
            }
        }
    }
}
