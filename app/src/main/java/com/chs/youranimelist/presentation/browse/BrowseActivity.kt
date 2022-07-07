package com.chs.youranimelist.presentation.browse

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chs.youranimelist.presentation.BottomNavScreen
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailScreen
import com.chs.youranimelist.presentation.browse.character.CharacterDetailScreen
import com.chs.youranimelist.presentation.browse.studio.StudioDetailScreen
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val activity = (LocalContext.current as? Activity)
            YourAnimeListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                IconButton(onClick = {
                                    activity?.finish()
                                }) {
                                    Icon(Icons.Filled.Close, null)
                                }
                            }
                        )
                    }
                ) {
                    val startMediaDestination =
                        if (intent?.getStringExtra(Constant.TARGET_TYPE) == Constant.TARGET_MEDIA) {
                            "${BrowseScreen.AnimeDetailScreen.route}/{id}/{idMal}"
                        } else {
                            "${BrowseScreen.CharacterDetailScreen.route}/{id}"
                        }

                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(it),
                        startDestination = startMediaDestination
                    ) {
                        composable(
                            route = "${BrowseScreen.AnimeDetailScreen.route}/{id}/{idMal}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType; defaultValue =
                                    intent?.getIntExtra(Constant.TARGET_ID, 0)!!
                                },
                                navArgument("idMal") {
                                    type = NavType.IntType; defaultValue =
                                    intent?.getIntExtra(Constant.TARGET_ID_MAL, 0)!!
                                },
                            )
                        ) { backStackEntry ->
                            AnimeDetailScreen(
                                id = backStackEntry.arguments?.getInt("id")!!,
                                idMal = backStackEntry.arguments?.getInt("idMal")!!,
                                navController
                            )
                        }

                        composable("${BrowseScreen.CharacterDetailScreen.route}/{id}") { backStackEntry ->
                            CharacterDetailScreen()
                        }
                        composable("${BrowseScreen.StudioDetailScreen.route}/{id}") { backStackEntry ->
                            StudioDetailScreen()
                        }
                    }
                }
            }
        }
    }
}
