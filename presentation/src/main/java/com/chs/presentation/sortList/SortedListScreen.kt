package com.chs.presentation.sortList

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.common.UiConst
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.FilterDialog
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.items
import com.chs.presentation.ui.theme.Pink80

@Composable
fun SortedListScreen(
    sortOption: String? = null,
    sortYear: Int,
    sortSeason: String? = null,
    genre: String? = null,
    viewModel: SortedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()
    val pagingItems = state.animeSortPaging?.collectAsLazyPagingItems()
    var filterDialogShow by remember { mutableStateOf(false) }
    var placeItemShow by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.initSort(
            selectType = UiConst.SortType.values().firstOrNull { it.rawValue == sortOption },
            selectYear = sortYear,
            selectSeason = UiConst.Season.values().firstOrNull { it.rawValue == sortSeason },
            selectGenre = genre
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(state.menuList.size) { idx ->
                ItemSort(
                    title = state.menuList[idx].first,
                    subTitle = viewModel.getSelectedOption(idx)
                ) {
                    viewModel.selectMenuIdx =
                        state.menuList.indexOf(state.menuList.find { it.first == state.menuList[idx].first })
                    filterDialogShow = true
                }
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
                    pagingItems,
                    key = { it.id }
                ) { animeInfo ->
                    if (animeInfo != null) {
                        ItemAnimeSmall(item = animeInfo) {
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                    this.putExtra(UiConst.TARGET_ID, animeInfo.id)
                                    this.putExtra(UiConst.TARGET_ID_MAL, animeInfo.idMal)
                                }
                            )
                        }
                    }
                }
            }

            if (placeItemShow) {
                items(9) {
                    Card(
                        modifier = Modifier
                            .width(130.dp)
                            .height(280.dp)
                    ) {

                    }
                }
            }
        }
    }

    if (filterDialogShow) {
        FilterDialog(
            list = state.menuList[viewModel.selectMenuIdx].second,
            onDismiss = {
                filterDialogShow = false
            }, onClick = { selectIdx ->
                viewModel.changeFilterOptions(selectIdx)
            }
        )
    }

    if (pagingItems != null) {
        placeItemShow = when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                true
            }

            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> false
        }

        placeItemShow = when (pagingItems.loadState.append) {
            is LoadState.Loading -> {
                true
            }

            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> false
        }
    }
}

@Composable
private fun ItemSort(
    title: String,
    subTitle: String,
    clickAble: (String) -> Unit
) {
    Text(
        text = title,
        color = Pink80
    )
    TextButton(
        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
        onClick = { clickAble(title) }
    ) {
        Text(text = subTitle)
    }
}