package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadState
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.common.ItemAnimeLarge
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

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
                is LoadStateLoading -> {
                    items(10) {
                        ItemAnimeLarge(anime = null) { }
                    }
                }

                is LoadStateError -> {
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
                is LoadStateLoading -> {
                    items(10) {
                        ItemAnimeLarge(anime = null) { }
                    }
                }

                is LoadStateError -> {
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