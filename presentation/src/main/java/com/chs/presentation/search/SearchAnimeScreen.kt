package com.chs.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchAnimeScreen(
    pagingItem: Flow<PagingData<AnimeInfo>>?,
    onEvent: (SearchEvent) -> Unit
) {
    val lazyColScrollState = rememberLazyListState()
    val animeItems = pagingItem?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (animeItems != null) {
            items(
                count = animeItems.itemCount,
                key = animeItems.itemKey(key = { it.id })
            ) { index ->
                val item = animeItems[index]
                ItemAnimeLarge(anime = item) {
                    if (item != null) {
                        onEvent(
                            SearchEvent.Click.Anime(
                                id = item.id,
                                idMal = item.idMal
                            )
                        )
                    }
                }
            }

            when (animeItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemAnimeLarge(anime = null) { }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> {
                    if (animeItems.itemCount == 0) {
                        item {
                            ItemNoResultImage()
                        }
                    }
                }
            }

            when (animeItems.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemAnimeLarge(anime = null) { }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }
        }

    }
}