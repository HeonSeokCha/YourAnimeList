package com.chs.presentation.sortList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemErrorImage
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .horizontalScroll(scrollState)
                    .clickable { filterDialogShow = true },
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
                    subTitle = UiConst.SortType.entries.find { it.rawValue == state.sortFilter.selectSort }!!.name
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
                                ItemErrorImage(message = (pagingItems.loadState.refresh as LoadState.Error).error.message)
                            }
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
    }

    if (filterDialogShow) {
        ModalBottomSheet(
            onDismissRequest = { filterDialogShow = false }
        ) {
            SortFilterDialog(
                selectedSortFilter = state.sortFilter,
                yearOptionList = state.optionYears,
                seasonOptionList = state.optionSeason,
                sortOptionList = state.optionSort,
                statusOptionList = state.optionStatus,
                genreOptionList = state.optionGenres,
                tagOptionList = state.optionTags,
            ) {
                onEvent(SortEvent.ChangeSortOption(it))
                filterDialogShow = false
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
        color = Pink80
    )

    Text(text = subTitle)
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