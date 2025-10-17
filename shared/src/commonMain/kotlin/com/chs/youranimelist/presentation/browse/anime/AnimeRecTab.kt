package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.common.ItemAnimeLarge
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimeRecScreen(
    state: AnimeDetailState,
    animeRecList: Flow<PagingData<AnimeInfo>>,
    onIntent: (AnimeDetailIntent) -> Unit
) {
    val scrollState = rememberLazyListState()
    val pagingItems = animeRecList.collectAsLazyPagingItems()

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
                onIntent(AnimeDetailIntent.OnLoadRecList)
            }

            is LoadState.Error ->  onIntent(AnimeDetailIntent.OnErrorRecList)

            is LoadState.NotLoading -> onIntent(AnimeDetailIntent.OnLoadCompleteRecList)
        }
    }

    LaunchedEffect(pagingItems.loadState.append) {
        when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                onIntent(AnimeDetailIntent.OnAppendLoadRecList)
            }

            is LoadState.Error -> onIntent(AnimeDetailIntent.OnErrorRecList)

            is LoadState.NotLoading -> onIntent(AnimeDetailIntent.OnAppendLoadCompleteRecList)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        state = scrollState,
        contentPadding = PaddingValues(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        when {
            state.animeRecListisError -> {
                item {
                }
            }
            state.animeRecListLoading -> {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            }

            isEmpty -> {
                item {
                    ItemNoResultImage(modifier = Modifier.fillParentMaxSize())
                }
            }

            else -> {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey(key = { it.id })
                ) { index ->
                    val item = pagingItems[index]
                    ItemAnimeLarge(item) {
                        if (item == null) return@ItemAnimeLarge
                        onIntent(AnimeDetailIntent.ClickAnime(id = item.id, idMal = item.idMal))
                    }
                }

                if (state.animeRecListAppendLoading) {
                    items(10) {
                        ItemAnimeLarge(null) {}
                    }
                }
            }
        }
    }
}