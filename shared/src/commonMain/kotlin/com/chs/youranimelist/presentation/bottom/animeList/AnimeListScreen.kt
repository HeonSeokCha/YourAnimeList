package com.chs.youranimelist.presentation.bottom.animeList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import com.chs.youranimelist.presentation.common.ItemAnimeLarge

@Composable
fun AnimeListScreenRoot(
    viewModel: AnimeListViewModel,
    onNavigateAnimeDetail: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AnimeListEffect.NavigateAnimeDetail -> {
                    onNavigateAnimeDetail(
                        BrowseInfo(type = MediaType.MEDIA, id = effect.id, idMal = effect.idMal)
                    )
                }
            }
        }
    }

    AnimeListScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun AnimeListScreen(
    state: AnimeListState,
    onIntent: (AnimeListIntent) -> Unit
) {
    if (!state.isLoading && state.list.isEmpty()) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            when {
                state.isLoading -> {
                    items(UiConst.BANNER_SIZE) {
                        ItemAnimeLarge()
                    }
                }

                state.isEmpty -> {
                    item {
                        ItemNoResultImage(modifier = Modifier.fillParentMaxSize())
                    }
                }

                else -> {
                    items(
                        items = state.list,
                        key = { it.id }
                    ) { animeInfo ->
                        ItemAnimeLarge(anime = animeInfo) {
                            onIntent(
                                AnimeListIntent.ClickAnime(
                                    id = animeInfo.id,
                                    idMal = animeInfo.idMal
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
