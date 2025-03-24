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
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.common.ItemCharaLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchCharaScreen(
    pagingItems: Flow<PagingData<CharacterInfo>>?,
    onEvent: (SearchEvent) -> Unit
) {
    val lazyColScrollState = rememberLazyListState()
    val charaItems = pagingItems?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (charaItems != null) {
            items(
                count = charaItems.itemCount,
                key = charaItems.itemKey(key = { it.id })
            ) { idx ->
                val item = charaItems[idx]
                ItemCharaLarge(item) { id ->
                    if (item != null) {
                        onEvent(
                            SearchEvent.Click.Chara(id = id)
                        )
                    }
                }
            }

            when (charaItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemCharaLarge(character = null) { }
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
                    if (charaItems.itemCount == 0) {
                        item {
                            ItemNoResultImage()
                        }
                    }
                }
            }

            when (charaItems.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemCharaLarge(character = null) { }
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