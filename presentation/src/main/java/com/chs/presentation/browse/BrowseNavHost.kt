package com.chs.presentation.browse

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.common.Constants
import com.chs.presentation.UiConst
import com.chs.presentation.browse.actor.ActorDetailScreenRoot
import com.chs.presentation.browse.actor.ActorDetailViewModel
import com.chs.presentation.browse.anime.AnimeDetailScreenRoot
import com.chs.presentation.browse.anime.AnimeDetailViewModel
import com.chs.presentation.browse.character.CharacterDetailScreenRoot
import com.chs.presentation.browse.character.CharacterDetailViewModel
import com.chs.presentation.browse.studio.StudioDetailScreenRoot
import com.chs.presentation.browse.studio.StudioDetailViewModel
import com.chs.presentation.main.Screen
import com.chs.presentation.sortList.SortedListScreenRoot
import com.chs.presentation.sortList.SortedViewModel

@Composable
fun BrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    intent: Intent?
) {

    val activity = LocalActivity.current
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
                    activity?.startActivity(
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
                onLinkClick = { url ->
                    activity?.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                },
                onTagClick = { tag ->
                    navController.navigate(
                        Screen.SortList(tag = tag)
                    )
                }
            )
        }

        composable<BrowseScreen.CharacterDetail> {
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
                },
                onCharaClick = { id ->
                    navController.navigate(
                        BrowseScreen.CharacterDetail(id = id)
                    )
                },
                onVoiceActorClick = { id ->
                    navController.navigate(
                        BrowseScreen.ActorDetail(id = id)
                    )
                },
                onLinkClick = { url ->
                    activity?.startActivity(
                        Intent(Intent.ACTION_VIEW, url.toUri())
                    )
                },
                onCloseClick = {
                    activity?.finish()
                }
            )
        }

        composable<BrowseScreen.StudioDetail> {
            val arg = it.toRoute<BrowseScreen.StudioDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewmodel: StudioDetailViewModel = hiltViewModel(parentEntry)

            StudioDetailScreenRoot(
                viewModel = viewmodel,
                onAnimeClick = { id, idMal ->
                    navController.navigate(
                        BrowseScreen.AnimeDetail(id = id, idMal = idMal)
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
            SortedListScreenRoot(
                viewModel = viewmodel,
                onClickAnime = { id, idMal ->
                    navController.navigate(
                        BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                    )
                }
            )
        }

        composable<BrowseScreen.ActorDetail> {
            val arg = it.toRoute<BrowseScreen.ActorDetail>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }

            val viewModel: ActorDetailViewModel = hiltViewModel(parentEntry)
            ActorDetailScreenRoot(
                viewModel = viewModel,
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
                onLinkClick = { url ->
                    activity?.startActivity(
                        Intent(Intent.ACTION_VIEW, url.toUri())
                    )
                },
                onCloseClick = {
                    activity?.finish()
                }
            )
        }
    }
}