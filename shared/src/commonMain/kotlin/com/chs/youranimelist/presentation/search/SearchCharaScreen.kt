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
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.presentation.common.ItemCharaLarge
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchCharaScreen(
    state: SearchState,
    pagingItem: Flow<PagingData<CharacterInfo>>,
    onIntent: (SearchIntent) -> Unit
) {
    val lazyColScrollState = rememberLazyListState()
    val charaItems = pagingItem.collectAsLazyPagingItems()
    val isRefresh = charaItems.loadState.refresh is LoadState.Loading
    val isEmpty by remember {
        derivedStateOf {
            !isRefresh
                    && charaItems.loadState.refresh is LoadState.NotLoading
                    && charaItems.loadState.append.endOfPaginationReached
                    && charaItems.itemCount == 0
        }
    }

    LaunchedEffect(charaItems.loadState.refresh) {
        when (charaItems.loadState.refresh) {
            is LoadState.Loading -> {
                lazyColScrollState.animateScrollToItem(0)
                onIntent(SearchIntent.LoadChara)
            }

            is LoadState.Error -> {
                (charaItems.loadState.refresh as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(SearchIntent.LoadCompleteChara)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when {
            state.isCharaLoading -> {
                items(10) {
                    ItemCharaLarge(character = null) { }
                }
            }

            isEmpty -> {
                item {
                    ItemNoResultImage(modifier = Modifier.fillParentMaxSize())
                }
            }

            else -> {
                items(
                    count = charaItems.itemCount,
                    key = charaItems.itemKey(key = { it.id })
                ) { idx ->
                    val item = charaItems[idx]
                    ItemCharaLarge(item) { id ->
                        if (item == null) return@ItemCharaLarge
                        onIntent(SearchIntent.ClickChara(id = id))
                    }
                }
            }
        }
    }
}