package com.chs.youranimelist.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.browse.studio.StudioDetailIntent
import com.chs.youranimelist.presentation.common.ItemActorMedia
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.common.ItemExpandSingleBox
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ActorMediaTab(
    state: ActorDetailState,
    pagingData: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>,
    onIntent: (ActorDetailIntent) -> Unit
) {
    val listState = rememberLazyGridState()
    val pagingItems = pagingData.collectAsLazyPagingItems()
    val isEmpty by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.NotLoading
                    && pagingItems.loadState.append.endOfPaginationReached
                    && pagingItems.itemCount == 0
        }
    }


    LaunchedEffect(pagingItems.loadState.refresh) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                listState.scrollToItem(0, 0)
                onIntent(ActorDetailIntent.OnLoad)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.refresh as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(ActorDetailIntent.OnLoadComplete)
        }
    }

    LaunchedEffect(pagingItems.loadState.append) {
        when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                onIntent(ActorDetailIntent.OnAppendLoad)
            }

            is LoadState.Error -> {
                (pagingItems.loadState.append as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(ActorDetailIntent.OnAppendLoadComplete)
        }
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        state = listState,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 8.dp
        )
    ) {
        item(span = { GridItemSpan(3) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                ItemExpandSingleBox(
                    title = "Sort",
                    list = SortType.entries.toList()
                        .filterNot { it == SortType.TRENDING },
                    initValue = state.selectOption,
                ) { selectValue ->
                    if (selectValue == null) return@ItemExpandSingleBox
                    onIntent(ActorDetailIntent.ChangeSortOption(selectValue))
                }
            }
        }

        when {
            state.isLoading -> {
                items(count = UiConst.BANNER_SIZE) {
                    ItemActorMedia(
                        info = null,
                        onAnimeClick = { _, _ -> },
                        onCharaClick = { }
                    )
                }
            }

            isEmpty -> {
                item(span = { GridItemSpan(3) }) {
                    ItemNoResultImage(modifier = Modifier.fillMaxSize())
                }
            }

            else -> {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { "${it.first.id}/${it.second.id}" }
                ) {
                    ItemActorMedia(
                        info = pagingItems[it],
                        onAnimeClick = { id, idMal ->
                            onIntent(ActorDetailIntent.ClickAnime(id, idMal))
                        },
                        onCharaClick = { id ->
                            onIntent(ActorDetailIntent.ClickChara(id))
                        }
                    )
                }

                if (state.isAppendLoading) {
                    items(count = UiConst.BANNER_SIZE) {
                        ItemActorMedia(
                            info = null,
                            onAnimeClick = { _, _ -> },
                            onCharaClick = { }
                        )
                    }
                }
            }
        }
    }
}