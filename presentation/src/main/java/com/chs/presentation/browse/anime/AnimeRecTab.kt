package com.chs.presentation.browse.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimeRecScreen(
    animeRecList: Flow<PagingData<AnimeInfo>>,
    onNavigate: (Int, Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val lazyPagingItems = animeRecList.collectAsLazyPagingItems()

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
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey(key = { it.id })
        ) { index ->
            val item = lazyPagingItems[index]
            ItemAnimeLarge(item) {
                if (item != null) {
                    onNavigate(
                        item.id,
                        item.idMal
                    )
                }
            }
        }

        when (lazyPagingItems.loadState.refresh) {
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
                if (lazyPagingItems.itemCount == 0) {
                    item {
                        ItemNoResultImage()
                    }
                }
            }
        }

        when (lazyPagingItems.loadState.append) {
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