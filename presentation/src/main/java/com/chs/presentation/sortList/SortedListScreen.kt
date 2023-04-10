package com.chs.presentation.sortList

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.ui.theme.Pink80
import com.chs.common.UiConst
import com.chs.presentation.common.ItemAnimeSmall

@Composable
fun SortedListScreen(
    sortOption: String? = null,
    sortYear: String? = null,
    sortSeason: String? = null,
    genre: String? = null,
    viewModel: SortedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()
    val pagingItems = state.animeSortPaging?.collectAsLazyPagingItems()
    var filterDialogShow by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.initSort(
            selectType = UiConst.SortType.values().firstOrNull { it.rawValue == sortOption },
            selectYear = sortYear,
            selectSeason = UiConst.Season.values().firstOrNull { it.rawValue == sortSeason },
            selectGenre = genre
        )
        viewModel.getGenreList()
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
            items(pagingItems?.itemCount ?: 0) { idx ->
                ItemAnimeSmall(item = pagingItems?.get(idx)!!) {
                    context.startActivity(
                        Intent(
                            context, BrowseActivity::class.java
                        ).apply {
                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                            this.putExtra(UiConst.TARGET_ID, pagingItems[idx]!!.id)
                            this.putExtra(
                                UiConst.TARGET_ID_MAL,
                                pagingItems[idx]!!.idMal
                            )
                        }
                    )
                }
            }
        }
    }

    if (filterDialogShow) {
        filterDialog(state.menuList[viewModel.selectMenuIdx].second, onDismiss = {
            filterDialogShow = false
        }, onClick = { selectIdx ->
            viewModel.changeFilterOptions(selectIdx)
        })
    }
    if (pagingItems?.loadState?.append == LoadState.Loading) {
        LoadingIndicator()
    }
    when (pagingItems?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            LoadingIndicator()
        }

        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}

@Composable
fun ItemSort(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun filterDialog(
    list: List<Pair<String, String>>,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .background(color = Color.White)
            .padding(
                top = 64.dp,
                bottom = 64.dp,
                start = 16.dp,
                end = 16.dp
            ),
        onDismissRequest = onDismiss,
    ) {
        LazyColumn(
            modifier = Modifier.background(Color.Gray),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list.size) { idx ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDismiss()
                            onClick(idx)
                        },
                    text = list[idx].second,
                    fontSize = 16.sp
                )
            }
        }
    }
}