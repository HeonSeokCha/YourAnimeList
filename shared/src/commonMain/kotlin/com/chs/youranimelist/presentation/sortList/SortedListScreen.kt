package com.chs.youranimelist.presentation.sortList

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.common.ItemErrorImage
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import com.chs.youranimelist.presentation.common.ItemPullToRefreshBox
import com.chs.youranimelist.presentation.ui.theme.Red200
import com.chs.youranimelist.presentation.ui.theme.Red500
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SortedListScreenRoot(
    viewModel: SortedViewModel,
    onClickAnime: (Int, Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    SortedListScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is SortEvent.ClickAnime -> {
                    onClickAnime(
                        event.id,
                        event.idMal
                    )
                }

                else -> Unit
            }
            viewModel.changeSortEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortedListScreen(
    state: SortState,
    onEvent: (SortEvent) -> Unit
) {
    val pagingItems = state.animeSortPaging?.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()
    val listState = rememberLazyGridState()

    LaunchedEffect(state.sortFilter) {
        onEvent(SortEvent.GetSortList)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEvent(SortEvent.OnChangeDialogState) },
                expanded = listState.isScrollingUp(),
                icon = { Icon(Icons.Filled.Tune, null) },
                text = { Text(text = "Filter") },
                containerColor = Red200
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {

        ItemPullToRefreshBox(
            isRefreshing = state.isRefresh,
            onRefresh = {
                coroutineScope.launch {
                    onEvent(SortEvent.OnRefresh)
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .horizontalScroll(scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ItemSort(
                        title = "Year",
                        subTitle = if (state.sortFilter.selectYear == null) "Any" else state.sortFilter.selectYear.toString()
                    )

                    ItemSort(
                        title = "Season",
                        subTitle = UiConst.Season.entries.find { it.rawValue == state.sortFilter.selectSeason }?.name
                            ?: "Any"
                    )

                    ItemSort(
                        title = "Sort",
                        subTitle = UiConst.SortType.entries.find { it.rawValue == state.sortFilter.selectSort.first() }!!.name
                    )

                    ItemSort(
                        title = "Status",
                        subTitle = UiConst.mediaStatus.entries.find { it.key == state.sortFilter.selectStatus }?.value?.first
                            ?: "Any"
                    )

                    ItemSort(
                        title = "Genres",
                        subTitle = if (state.sortFilter.selectGenre != null) {
                            if (state.sortFilter.selectGenre!!.size == 1) {
                                state.sortFilter.selectGenre!!.first()
                            } else {
                                "${state.sortFilter.selectGenre!!.first()} + ${state.sortFilter.selectGenre!!.size - 1}"
                            }
                        } else {
                            "Any"
                        }
                    )

                    ItemSort(
                        title = "Tags",
                        subTitle = if (state.sortFilter.selectTags != null) {
                            if (state.sortFilter.selectTags!!.size == 1) {
                                state.sortFilter.selectTags!!.first()
                            } else {
                                "${state.sortFilter.selectTags!!.first()} + ${state.sortFilter.selectTags!!.size - 1}"
                            }
                        } else {
                            "Any"
                        }
                    )
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
                    if (pagingItems != null) {

                        items(
                            count = pagingItems.itemCount,
                            key = pagingItems.itemKey { it.id }
                        ) {
                            val animeInfo = pagingItems[it]
                            ItemAnimeSmall(item = animeInfo) {
                                if (animeInfo != null) {
                                    onEvent(
                                        SortEvent.ClickAnime(
                                            id = animeInfo.id,
                                            idMal = animeInfo.idMal
                                        )
                                    )
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
                                    ItemErrorImage(message = (pagingItems.loadState.refresh as LoadState.Error).error.message)
                                }
                            }

                            else -> {
                                if (pagingItems.itemCount == 0) {
                                    item {
                                        ItemNoResultImage()
                                    }
                                }
                            }
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
        }
    }

    if (state.isShowDialog) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onEvent(SortEvent.OnChangeDialogState)
            }
        ) {
            SortFilterDialog(
                selectedSortFilter = state.sortFilter,
                sortOptions = state.sortOptions
            ) {
                coroutineScope.launch { listState.scrollToItem(0) }
                onEvent(SortEvent.ChangeSortOption(it))
            }
        }
    }
}

@Composable
private fun ItemSort(
    title: String,
    subTitle: String,
) {
    Text(
        text = title,
        fontSize = 12.sp,
    )

    Spacer(Modifier.width(8.dp))

    Text(
        text = subTitle,
        fontSize = 13.sp,
        color = Red500
    )

    Spacer(Modifier.width(8.dp))
}

@Composable
private fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Preview
@Composable
private fun PreviewSortedListScreen() {
    SortedListScreen(
        state = SortState(),
        onEvent = {}
    )
}