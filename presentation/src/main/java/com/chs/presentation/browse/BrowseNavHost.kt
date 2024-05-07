package com.chs.presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.chs.presentation.sortList.SortedViewModel

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
        ) {
            AnimeDetailScreen(navController)
        }

        composable(
            route = "${BrowseScreen.CharacterDetailScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = intent?.getIntExtra(UiConst.TARGET_ID, 0)!!
                }
            )
        ) {
            CharacterDetailScreen(navController)
        }

        composable(
            route = "${BrowseScreen.StudioDetailScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = intent?.getIntExtra(UiConst.TARGET_ID, 0)!!
                }
            )
        ) {
            StudioDetailScreen(navController)
        }

        composable(
            route = "${Screen.SortListScreen.route}/{year}/{season}",
            arguments = listOf(
                navArgument("year") {
                    defaultValue = intent?.getIntExtra(UiConst.KEY_YEAR, 0)
                    type = NavType.IntType
                },
                navArgument("season") {
                    defaultValue = intent?.getStringExtra(UiConst.KEY_SEASON) ?: ""
                    type = NavType.StringType
                }
            )
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.SortListScreen.route)
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)
            SortedListScreen(
                state = viewmodel.state,
                onChangeOption = viewmodel::changeSortEvent
            )
        }

        composable(
            route = "${Screen.SortListScreen.route}/{genre}",
            arguments = listOf(
                navArgument("genre") {
                    defaultValue = intent?.getStringExtra(UiConst.KEY_GENRE) ?: ""
                    type = NavType.StringType
                }
            )
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.SortListScreen.route)
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)
            SortedListScreen(
                state = viewmodel.state,
                onChangeOption = viewmodel::changeSortEvent
            )
        }
    }
}