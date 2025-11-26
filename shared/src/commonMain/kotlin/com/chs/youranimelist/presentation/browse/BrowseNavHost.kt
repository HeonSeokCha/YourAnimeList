package com.chs.youranimelist.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.domain.model.SortFilter
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
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BrowseNavHost(
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
    val module = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(BrowseScreen.AnimeDetail::class, BrowseScreen.AnimeDetail.serializer())
            subclass(BrowseScreen.CharacterDetail::class, BrowseScreen.CharacterDetail.serializer())
            subclass(BrowseScreen.StudioDetail::class, BrowseScreen.StudioDetail.serializer())
            subclass(BrowseScreen.ActorDetail::class, BrowseScreen.ActorDetail.serializer())
        }
    }

    val config = SavedStateConfiguration { serializersModule = module }
    val backStack = rememberNavBackStack(configuration = config, startMediaDestination)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BrowseScreen.AnimeDetail> { key ->
                val viewmodel: AnimeDetailViewModel = koinViewModel {
                    parametersOf(key.id, key.idMal)
                }
                AnimeDetailScreenRoot(
                    viewModel = viewmodel,
                    onCloseClick = { onClose() },
                    onTrailerClick = { trailerId ->
                        onLinkClick("https://youtube.com/watch?v=$trailerId")
                    },
                    onAnimeClick = { id, idMal ->
                        backStack.add(
                            BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                        )
                    },
                    onCharaClick = { id ->
                        backStack.add(BrowseScreen.CharacterDetail(id))
                    },
                    onStudioClick = { id ->
                        backStack.add(BrowseScreen.StudioDetail(id))
                    },
                    onGenreClick = { genre ->
                        backStack.add(Screen.SortList(SortFilter(selectGenre = listOf(genre))))
                    },
                    onSeasonYearClick = { season, year ->
                        backStack.add(Screen.SortList(SortFilter(selectSeason = season, selectYear = year)))
                    },
                    onLinkClick = { url -> onLinkClick(url) },
                    onTagClick = { tag ->
                        backStack.add(Screen.SortList(SortFilter(selectTags = listOf(tag))))
                    }
                )
            }

            entry<BrowseScreen.CharacterDetail> { key ->
                val viewmodel: CharacterDetailViewModel = koinViewModel {
                    parametersOf(key.id)
                }
                CharacterDetailScreenRoot(
                    viewModel = viewmodel,
                    onAnimeClick = { id: Int, idMal: Int ->
                        backStack.add(
                            BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                        )
                    },
                    onCharaClick = { id ->
                        backStack.add(
                            BrowseScreen.CharacterDetail(id = id)
                        )
                    },
                    onVoiceActorClick = { id ->
                        backStack.add(
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

            entry<BrowseScreen.StudioDetail> { key ->
                val viewmodel: StudioDetailViewModel = koinViewModel {
                    parametersOf(key.id)
                }

                StudioDetailScreenRoot(
                    viewModel = viewmodel,
                    onAnimeClick = { id, idMal ->
                        backStack.add(BrowseScreen.AnimeDetail(id = id, idMal = idMal))
                    }, onCloseClick = { onClose() }
                )
            }

            entry<Screen.SortList> { key ->
                val viewmodel: SortedViewModel = koinViewModel {
                    parametersOf(key.filter)
                }
                SortedListScreenRoot(
                    viewModel = viewmodel,
                    onClickAnime = { id, idMal ->
                        backStack.add(
                            BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                        )
                    }
                )
            }

            entry<BrowseScreen.ActorDetail> { key ->
                val viewModel: ActorDetailViewModel = koinViewModel {
                    parametersOf(key.id)
                }
                ActorDetailScreenRoot(
                    viewModel = viewModel,
                    onAnimeClick = { id, idMal ->
                        backStack.add(
                            BrowseScreen.AnimeDetail(id = id, idMal = idMal)
                        )
                    },
                    onCharaClick = { id ->
                        backStack.add(
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
    )
}