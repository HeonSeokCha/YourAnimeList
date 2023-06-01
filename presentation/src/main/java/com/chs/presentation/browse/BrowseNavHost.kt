package com.chs.presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.presentation.browse.anime.detail.AnimeDetailScreen
import com.chs.presentation.browse.character.CharacterDetailScreen
import com.chs.presentation.UiConst
import com.chs.presentation.browse.studio.StudioDetailScreen
import com.chs.presentation.main.Screen
import com.chs.presentation.sortList.SortedListScreen

@Composable
fun BrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    intent: Intent?
) {

    val startMediaDestination =
        if (intent?.getStringExtra(UiConst.TARGET_TYPE) == UiConst.TARGET_MEDIA) {
            "${BrowseScreen.AnimeDetailScreen.route}/{id}/{idMal}"
        } else {
            "${BrowseScreen.CharacterDetailScreen.route}/{id}"
        }

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startMediaDestination
    ) {
        composable(
            route = "${BrowseScreen.AnimeDetailScreen.route}/{id}/{idMal}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = intent?.getIntExtra(UiConst.TARGET_ID, 0)!!
                },
                navArgument("idMal") {
                    type = NavType.IntType
                    defaultValue = intent?.getIntExtra(UiConst.TARGET_ID_MAL, 0)!!
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
                    intent?.getIntExtra(UiConst.TARGET_ID, 0)!!
                }
            )
        ) { backStackEntry ->
            CharacterDetailScreen(
                backStackEntry.arguments?.getInt("id")!!,
                navController
            )
        }

        composable(
            route = "${BrowseScreen.StudioDetailScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    defaultValue = 0
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            StudioDetailScreen(
                studioId = backStackEntry.arguments?.getInt("id") ?: 0,
                navController
            )
        }

        composable(
            route = "${Screen.SortListScreen.route}/{year}/{season}",
            arguments = listOf(
                navArgument("year") {
                    defaultValue = 0
                    type = NavType.IntType
                },
                navArgument("season") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            SortedListScreen(
                sort = UiConst.SortType.POPULARITY.rawValue,
                year = backStackEntry.arguments?.getInt("year") ?: 0,
                season = backStackEntry.arguments?.getString("season") ?: ""
            )
        }

        composable("${Screen.SortListScreen.route}/{genre}") { backStackEntry ->
            SortedListScreen(
                sort = UiConst.SortType.AVERAGE.rawValue,
                year = 0,
                season = null,
                genre = backStackEntry.arguments?.getString("genre") ?: ""
            )
        }
    }
}