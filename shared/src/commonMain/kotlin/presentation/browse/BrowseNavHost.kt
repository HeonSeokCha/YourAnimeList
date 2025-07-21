package presentation.browse

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.common.Constants
import presentation.UiConst
import presentation.browse.actor.ActorDetailScreenRoot
import presentation.browse.actor.ActorDetailViewModel
import presentation.browse.anime.AnimeDetailScreenRoot
import presentation.browse.anime.AnimeDetailViewModel
import presentation.browse.character.CharacterDetailScreenRoot
import presentation.browse.character.CharacterDetailViewModel
import presentation.browse.studio.StudioDetailScreenRoot
import presentation.browse.studio.StudioDetailViewModel
import presentation.main.Screen
import presentation.sortList.SortedListScreenRoot
import presentation.sortList.SortedViewModel
import org.koin.androidx.compose.koinViewModel

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
            val viewmodel: AnimeDetailViewModel = koinViewModel()
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
            val viewmodel: StudioDetailViewModel = koinViewModel()

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