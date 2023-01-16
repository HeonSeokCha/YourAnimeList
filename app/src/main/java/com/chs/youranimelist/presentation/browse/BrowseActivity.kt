package com.chs.youranimelist.presentation.browse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            YourAnimeListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BrowseNavHost(
                        navController = navController,
                        modifier = Modifier.padding(it),
                        intent = intent
                    )
                }
            }
        }
    }
}
