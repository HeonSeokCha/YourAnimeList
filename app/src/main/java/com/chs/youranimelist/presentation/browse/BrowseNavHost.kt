package com.chs.youranimelist.presentation.browse

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailScreen
import com.chs.youranimelist.presentation.browse.character.CharacterDetailScreen
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.sortList.SortedListScreen
import com.chs.youranimelist.util.Constant

@Composable
fun BrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    intent: Intent?
) {

    val startMediaDestination =
        if (intent?.getStringExtra(Constant.TARGET_TYPE) == Constant.TARGET_MEDIA) {
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

//        composable("${BrowseScreen.StudioDetailScreen.route}/{id}") {
//            StudioDetailScreen()
//        }

        composable("${Screen.SortListScreen.route}/{genre}") { backStackEntry ->
            SortedListScreen(
                sortType = Constant.TARGET_GENRE,
                genre = backStackEntry.arguments?.getString("genre")
                    ?: "",
            )
        }
    }
}