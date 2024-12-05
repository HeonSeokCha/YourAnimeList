package com.chs.presentation.browse

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.common.Constants
import com.chs.presentation.browse.anime.AnimeDetailScreen
import com.chs.presentation.browse.character.CharacterDetailScreen
import com.chs.presentation.UiConst
import com.chs.presentation.browse.actor.ActorDetailScreen
import com.chs.presentation.browse.actor.ActorDetailViewModel
import com.chs.presentation.browse.anime.AnimeDetailScreenRoot
import com.chs.presentation.browse.anime.AnimeDetailViewModel
import com.chs.presentation.browse.character.CharacterDetailScreenRoot
import com.chs.presentation.browse.character.CharacterDetailViewModel
import com.chs.presentation.browse.studio.StudioDetailScreen
import com.chs.presentation.browse.studio.StudioDetailScreenRoot
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
            BrowseScreen.AnimeDetail(
                id = intent.getIntExtra(UiConst.TARGET_ID, 0),
                idMal = intent.getIntExtra(UiConst.TARGET_ID_MAL, 0)
            )
        } else {
            BrowseScreen.CharacterDetail(
                id = intent!!.getIntExtra(UiConst.TARGET_ID, 0)
            )
        }

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startMediaDestination
    ) {

        composable<BrowseScreen.AnimeDetail> {
            val context = LocalContext.current
            val activity = (LocalContext.current as? Activity)
            val arg = it.toRoute<BrowseScreen.AnimeDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: AnimeDetailViewModel = hiltViewModel(parentEntry)
            AnimeDetailScreenRoot(
                viewModel = viewmodel,
                onCloseClick = {
                    activity?.finish()
                },
                onTrailerClick = { trailerId ->
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "${Constants.YOUTUBE_BASE_URL}$trailerId".toUri()
                        )
                    )
                },
                onAnimeClick = { id, idMal ->
                    navController.navigate(
                        BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                    )
                },
                onCharaClick = { id ->
                    navController.navigate(
                        BrowseScreen.CharacterDetail(id)
                    )
                },
                onStudioClick = { id ->
                    navController.navigate(
                        BrowseScreen.StudioDetail(id)
                    )
                },
                onGenreClick = { genre ->
                    navController.navigate(
                        Screen.SortList(genre = genre)
                    )
                },
                onSeasonYearClick = { year, season ->
                    navController.navigate(
                        Screen.SortList(year = year, season = season)
                    )
                },
                onTagClick = { tag ->
                    navController.navigate(
                        Screen.SortList(
                            tag = tag,
                            sortOption = UiConst.SortType.POPULARITY.rawValue
                        )
                    )
                }
            )
        }

        composable<BrowseScreen.CharacterDetail> {
            val activity = (LocalContext.current as? Activity)
            val arg = it.toRoute<BrowseScreen.CharacterDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: CharacterDetailViewModel = hiltViewModel(parentEntry)
            CharacterDetailScreenRoot(
                viewModel = viewmodel,
                onAnimeClick = { id: Int, idMal: Int ->
                    navController.navigate(
                        BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                    )
                }, onVoiceActorClick = { id ->
                    navController.navigate(
                        BrowseScreen.ActorDetail(id = id)
                    )
                }, onCloseClick = {
                    activity?.finish()
                }
            )
        }

        composable<BrowseScreen.StudioDetail> {
            val activity = (LocalContext.current as? Activity)
            val arg = it.toRoute<BrowseScreen.StudioDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: StudioDetailViewModel = hiltViewModel(parentEntry)

            StudioDetailScreenRoot(
                viewModel = viewmodel,
                onAnimeClick = { id, idMal ->
                    navController.navigate(
                        navController.navigate(
                            BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                        )
                    )
                }, onCloseClick = {
                    activity?.finish()
                }
            )
        }

        composable<Screen.SortList> {
            val arg = it.toRoute<Screen.SortList>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: SortedViewModel = hiltViewModel(parentEntry)
            val state by viewmodel.state.collectAsStateWithLifecycle()
            SortedListScreen(
                state = state,
                onEvent = viewmodel::changeSortEvent
            ) { id, idMal ->
                navController.navigate(BrowseScreen.AnimeDetail(id = id, idMal = idMal))
            }
        }

        composable<BrowseScreen.ActorDetail> {
            val arg = it.toRoute<BrowseScreen.ActorDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }

            val viewModel: ActorDetailViewModel = hiltViewModel(parentEntry)
            ActorDetailScreen(
                state = viewModel.state,
                onEvent = viewModel::changeEvent
            ) {
                navController.navigate(it)
            }
        }
    }
}