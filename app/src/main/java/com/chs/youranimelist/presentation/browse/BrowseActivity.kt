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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailScreen
import com.chs.youranimelist.presentation.browse.character.CharacterDetailScreen
import com.chs.youranimelist.presentation.browse.studio.StudioDetailScreen
import com.chs.youranimelist.presentation.sortList.SortedListScreen
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
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            YourAnimeListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (navBackStackEntry?.destination?.route == "${Screen.SortListScreen.route}/{genre}") {
                            TopAppBar(
                                title = {},
                                navigationIcon = {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(Icons.Filled.ArrowBack, null)
                                    }
                                }
                            )
                        } else {
                            TopAppBar(
                                title = {},
                                navigationIcon = {
                                    IconButton(
                                        onClick = { activity?.finish() }
                                    ) {
                                        Icon(Icons.Filled.Close, null)
                                    }
                                }
                            )
                        }
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
                                    type = NavType.IntType
                                    defaultValue = intent?.getIntExtra(Constant.TARGET_ID, 0)!!
                                },
                                navArgument("idMal") {
                                    type = NavType.IntType
                                    defaultValue = intent?.getIntExtra(Constant.TARGET_ID_MAL, 0)!!
                                },
                            )
                        ) { backStackEntry ->
                            AnimeDetailScreen(
                                id = backStackEntry.arguments?.getInt("id")!!,
                                idMal = backStackEntry.arguments?.getInt("idMal")!!,
                                navController
                            )
                        }

                        composable(
                            route = "${BrowseScreen.CharacterDetailScreen.route}/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType; defaultValue =
                                    intent?.getIntExtra(Constant.TARGET_ID, 0)!!
                                }
                            )
                        ) { backStackEntry ->
                            CharacterDetailScreen(
                                backStackEntry.arguments?.getInt("id")!!,
                                navController
                            )
                        }
                        composable("${BrowseScreen.StudioDetailScreen.route}/{id}") {
                            StudioDetailScreen()
                        }

                        composable("${Screen.SortListScreen.route}/{genre}") { backStackEntry ->
                            SortedListScreen(
                                sortType = Constant.TARGET_GENRE,
                                genre = backStackEntry.arguments?.getString("genre")
                                    ?: "",
                            )
                        }
                    }
                }
            }
        }
    }
}
