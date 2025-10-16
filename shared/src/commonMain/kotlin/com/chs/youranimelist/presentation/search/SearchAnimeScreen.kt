package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
fun SearchAnimeScreen(
    state: SearchState,
    pagingItem: Flow<PagingData<AnimeInfo>>,
    onIntent: (SearchIntent) -> Unit
) {
    val lazyColScrollState = rememberLazyListState()
    val animeItems = pagingItem.collectAsLazyPagingItems()
    val isRefresh = animeItems.loadState.refresh is LoadState.Loading

    val isEmpty by remember {
        derivedStateOf {
            !isRefresh
                    && animeItems.loadState.refresh is LoadState.NotLoading
                    && animeItems.loadState.append.endOfPaginationReached
                    && animeItems.itemCount == 0
        }
    }

    LaunchedEffect(animeItems.loadState.refresh) {
        when (animeItems.loadState.refresh) {
            is LoadState.Loading -> {
                lazyColScrollState.animateScrollToItem(0)
                onIntent(SearchIntent.LoadAnime)
            }

            is LoadState.Error -> {
                (animeItems.loadState.refresh as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.LoadCompleteAnime)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when {
            state.isAnimeLoading -> {
                items(10) {
                    ItemAnimeLarge(anime = null) { }
                }
            }
            isEmpty -> {
                item {
                    ItemNoResultImage(modifier = Modifier.fillParentMaxSize())
                }
            }

            else -> {
                items(
                    count = animeItems.itemCount,
                    key = animeItems.itemKey(key = { it.id })
                ) { index ->
                    val item = animeItems[index]
                    ItemAnimeLarge(anime = item) {
                        if (item == null) return@ItemAnimeLarge

                        onIntent(SearchIntent.ClickAnime(id = item.id, idMal = item.idMal))
                    }
                }
            }
        }
    }
}