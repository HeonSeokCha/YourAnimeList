package com.chs.presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.presentation.browse.anime.AnimeDetailScreen
import com.chs.presentation.browse.character.CharacterDetailScreen
import com.chs.presentation.UiConst
import com.chs.presentation.browse.anime.AnimeDetailViewModel
import com.chs.presentation.browse.character.CharacterDetailViewModel
import com.chs.presentation.browse.studio.StudioDetailScreen
import com.chs.presentation.browse.studio.StudioDetailViewModel
import com.chs.presentation.main.Screen
import com.chs.presentation.sortList.SortedListScreen
import com.chs.presentation.sortList.SortedViewModel

@Composable
fun BrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    intent: Intent?
) {

    val startMediaDestination: BrowseScreen =
        if (intent?.getStringExtra(UiConst.TARGET_TYPE) == UiConst.TARGET_MEDIA) {
            BrowseScreen.AnimeDetailScreen(
                id = intent.getIntExtra(UiConst.TARGET_ID, 0),
                idMal = intent.getIntExtra(UiConst.TARGET_ID_MAL, 0)
            )
        } else {
            BrowseScreen.CharacterDetailScreen(
                id = intent!!.getIntExtra(UiConst.TARGET_ID, 0)
            )
        }


    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startMediaDestination
    ) {
        composable<BrowseScreen.AnimeDetailScreen> {
            val arg = it.toRoute<BrowseScreen.AnimeDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: AnimeDetailViewModel = hiltViewModel(parentEntry)
            AnimeDetailScreen(
                state = viewmodel.state,
                onEvent = viewmodel::changeEvent
            ) {
                navController.navigate(it)
            }
        }

        composable<BrowseScreen.CharacterDetailScreen> {
            val arg = it.toRoute<BrowseScreen.CharacterDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: CharacterDetailViewModel = hiltViewModel(parentEntry)
            CharacterDetailScreen(
                state = viewmodel.state,
                onEvent = viewmodel::changeEvent
            ) {
                navController.navigate(it)
            }
        }

        composable<BrowseScreen.StudioDetailScreen> {
            val arg = it.toRoute<BrowseScreen.StudioDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: StudioDetailViewModel = hiltViewModel(parentEntry)
            StudioDetailScreen(
                state = viewmodel.state,
                onChangeOption = viewmodel::changeFilterOption
            ) {
                navController.navigate(it)
            }
        }


        composable<Screen.SortListScreen> {
            val arg = it.toRoute<Screen.SortListScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)
            SortedListScreen(
                state = viewmodel.state,
                onChangeOption = viewmodel::changeSortEvent
            )
        }
    }
}