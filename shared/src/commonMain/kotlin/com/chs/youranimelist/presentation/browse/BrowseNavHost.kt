package com.chs.youranimelist.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.browse.actor.ActorDetailScreenRoot
import com.chs.youranimelist.presentation.browse.actor.ActorDetailViewModel
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailScreenRoot
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailViewModel
import com.chs.youranimelist.presentation.browse.character.CharacterDetailScreenRoot
import com.chs.youranimelist.presentation.browse.character.CharacterDetailViewModel
import com.chs.youranimelist.presentation.browse.studio.StudioDetailScreenRoot
import com.chs.youranimelist.presentation.browse.studio.StudioDetailViewModel
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.sortList.SortedListScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BrowseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    browseInfo: BrowseInfo,
    onLinkClick: (String) -> Unit,
    onClose: () -> Unit
) {

    val startMediaDestination: BrowseScreen =
        if (browseInfo.type == MediaType.MEDIA) {
            BrowseScreen.AnimeDetail(
                id = browseInfo.id,
                idMal = browseInfo.idMal
            )
        } else {
            BrowseScreen.CharacterDetail(
                id = browseInfo.id
            )
        }

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startMediaDestination
    ) {
        composable<BrowseScreen.AnimeDetail> {
            val viewmodel: AnimeDetailViewModel = koinViewModel()
            AnimeDetailScreenRoot(
                viewModel = viewmodel,
                onCloseClick = {
                    onClose()
                },
                onTrailerClick = { trailerId ->
                    onLinkClick("https://youtube.com/watch?v=$trailerId}")
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
                    onLinkClick(url)
                },
                onTagClick = { tag ->
                    navController.navigate(
                        Screen.SortList(tag = tag)
                    )
                }
            )
        }

        composable<BrowseScreen.CharacterDetail> {
            val viewmodel: CharacterDetailViewModel = koinViewModel()
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
                    onLinkClick(url)
                },
                onCloseClick = {
                    onClose()
                }
            )
        }

        composable<BrowseScreen.StudioDetail> {
            val viewmodel: StudioDetailViewModel = koinViewModel()

            StudioDetailScreenRoot(
                viewModel = viewmodel,
                onAnimeClick = { id, idMal ->
                    navController.navigate(
                        BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                    )
                }, onCloseClick = {
                    onClose()
                }
            )
        }

        composable<Screen.SortList> {
            val viewmodel: SortedViewModel = koinViewModel()
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
            val viewModel: ActorDetailViewModel = koinViewModel()
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
                    onLinkClick(url)
                },
                onCloseClick = {
                    onClose()
                }
            )
        }
    }
}