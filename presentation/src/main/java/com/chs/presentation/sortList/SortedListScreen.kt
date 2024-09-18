package com.chs.presentation.sortList

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.presentation.common.FilterDialog
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemErrorImage
import com.chs.presentation.common.ItemNoResultImage
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.header
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortedListScreen(
    state: SortState,
    onEvent: (SortEvent) -> Unit,
    onActivityStart: (Int, Int) -> Unit
) {
    val lazyGridScrollState = rememberLazyGridState()
    val pagingItems = state.animeSortPaging?.collectAsLazyPagingItems()
    var filterDialogShow by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                onEvent(SortEvent.GetSortList)
                delay(500L)
                isRefreshing = false
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
            state = lazyGridScrollState,
            columns = GridCells.Fixed(3),
        ) {
            header {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                        .horizontalScroll(scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ItemSort(
                        title = "Year",
                        subTitle = if (state.selectYear == null) "Any" else state.selectYear.toString()
                    ) {
                        filterDialogShow = true
                    }

                    ItemSort(
                        title = "Season",
                        subTitle = state.selectSeason?.first ?: "Any"
                    ) {
                        filterDialogShow = true
                    }

                    ItemSort(
                        title = "Sort",
                        subTitle = state.selectSort?.first ?: "Any"
                    ) {
                        filterDialogShow = true
                    }

                    ItemSort(
                        title = "Status",
                        subTitle = state.selectStatus ?: "Any"
                    ) {
                        filterDialogShow = true
                    }

                    ItemSort(
                        title = "Genres",
                        subTitle = state.selectGenre?.first() ?: "Any"
                    ) {
                        filterDialogShow = true
                    }

                    ItemSort(
                        title = "Tags",
                        subTitle = state.selectTags?.first() ?: "Any"
                    ) {
                        filterDialogShow = true
                    }
                }
            }

            if (pagingItems != null) {

                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id }
                ) {
                    val animeInfo = pagingItems[it]
                    ItemAnimeSmall(item = animeInfo) {
                        if (animeInfo != null) {
                            onActivityStart(animeInfo.id, animeInfo.idMal)
                        }
                    }
                }

                when (pagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        items(10) {
                            ItemAnimeSmall(item = null)
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ItemErrorImage(message = (pagingItems.loadState.refresh as LoadState.Error).error.message) }
                    }

                    else -> Unit
                }


                when (pagingItems.loadState.append) {
                    is LoadState.Loading -> {
                        items(10) {
                            ItemAnimeSmall(item = null)
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            ItemErrorImage(message = (pagingItems.loadState.append as LoadState.Error).error.message)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    if (filterDialogShow) {
        ModalBottomSheet(
            onDismissRequest = {
                filterDialogShow = false
        }) {
            SortFilterDialog(state)
        }
    }
}

@Composable
private fun ItemSort(
    title: String,
    subTitle: String,
    clickAble: () -> Unit
) {
    Text(
        text = title,
        color = Pink80
    )
    TextButton(
        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
        onClick = { clickAble() }
    ) {
        Text(text = subTitle)
    }
}

@Preview
@Composable
private fun PreviewSortedListScreen() {
    SortedListScreen(
        state = SortState(),
        onEvent = {

        }, onActivityStart = { a, b ->

        }
    )
}