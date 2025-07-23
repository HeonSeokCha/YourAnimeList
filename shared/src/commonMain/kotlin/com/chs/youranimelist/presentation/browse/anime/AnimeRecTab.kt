package com.chs.youranimelist.presentation.browse.anime

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
fun AnimeRecScreen(
    animeRecList: Flow<PagingData<AnimeInfo>>,
    onNavigate: (Int, Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val lazyPagingItems = animeRecList.collectAsLazyPagingItems()
    var isLoading by remember { mutableStateOf(false) }
    var isEmpty by remember { mutableStateOf(false) }
    var isAppending by remember { mutableStateOf(false) }

    when (lazyPagingItems.loadState.refresh) {
        is LoadStateLoading -> isLoading = true

        is LoadStateError -> {
            isLoading = false
        }

        else -> {
            isLoading = false
            isEmpty = lazyPagingItems.itemCount == 0
        }
    }

    isAppending = when (lazyPagingItems.loadState.append) {
        is LoadStateLoading -> true

        else -> false
    }

    if (isEmpty) {
        ItemNoResultImage()
    } else {
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

            if (isLoading) {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            }

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

            if (isAppending) {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            }
        }
    }
}