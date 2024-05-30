package com.chs.presentation.sortList

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.FilterDialog
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.launch

@Composable
fun SortedListScreen(
    state: SortState,
    onChangeOption: (SortEvent) -> Unit
) {
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()
    val pagingItems = state.animeSortPaging?.collectAsLazyPagingItems()
    var filterDialogShow: Int? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = 4.dp,
                    end = 4.dp
                )
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ItemSort(
                title = "Year",
                subTitle = if (state.selectYear == null) "Any" else state.selectYear.toString()
            ) {
                filterDialogShow = 0
            }
            ItemSort(
                title = "Season",
                subTitle = state.selectSeason?.first ?: "Any"
            ) {
                filterDialogShow = 1
            }
            ItemSort(
                title = "Sort",
                subTitle = state.selectSort?.first ?: "Any"
            ) {
                filterDialogShow = 2
            }
            ItemSort(
                title = "Genre",
                subTitle = state.selectGenre ?: "Any"
            ) {
                filterDialogShow = 3
            }
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
                    key = { pagingItems[it]?.id ?: it }
                ) {
                    val animeInfo = pagingItems[it]
                    ItemAnimeSmall(item = animeInfo) {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                if (animeInfo != null) {
                                    this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                    this.putExtra(UiConst.TARGET_ID, animeInfo.id)
                                    this.putExtra(UiConst.TARGET_ID_MAL, animeInfo.idMal)
                                }
                            }
                        )
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
                            Text(
                                text = "Something Wrong for Loading List."
                            )
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

    if (filterDialogShow != null) {
        FilterDialog(
            list = when (filterDialogShow) {
                0 -> state.optionYears
                1 -> state.optionSeason
                2 -> state.optionSort
                3 -> state.optionGenres
                else -> state.optionYears
            },
            onDismiss = {
                filterDialogShow = null
            }, onClick = { selectValue ->
                coroutineScope.launch {
                    lazyGridScrollState.scrollToItem(0, 0)
                }
                when (filterDialogShow) {
                    0 -> {
                        onChangeOption(SortEvent.ChangeYearOption(selectValue.second.toInt()))
                    }

                    1 -> {
                        onChangeOption(SortEvent.ChangeSeasonOption(selectValue))
                    }

                    2 -> {
                        onChangeOption(SortEvent.ChangeSortOption(selectValue))
                    }

                    3 -> {
                        onChangeOption(SortEvent.ChangeGenreOption(selectValue.second))
                    }

                    else -> Unit
                }
            }
        )
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