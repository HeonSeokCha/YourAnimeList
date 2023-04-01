package com.chs.presentation.sortList

import android.content.Intent
import android.widget.Toast
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.ui.theme.Pink80
import com.chs.common.UiConst
import com.chs.presentation.ConvertDate
import com.chs.presentation.common.ItemAnimeSmall

@Composable
fun SortedListScreen(
    sortType: String,
    genre: String = "",
    viewModel: SortedViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()
    var list: List<String?> by remember { mutableStateOf(emptyList()) }
    var filterDialogShow by remember { mutableStateOf(false) }
    var filterSelect by remember { mutableStateOf("") }
    val pagingItems = viewModel.state.animeSortPaging?.collectAsLazyPagingItems()

    LaunchedEffect(viewModel, context) {
        viewModel.getSortedAnime()
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
            items(viewModel.filterList.size) { idx ->
                ItemSort(
                    title = viewModel.filterList[idx].first,
                    subTitle = viewModel.filterList[idx].second
                ) {
//                    when (viewModel.filterList[idx].first) {
//                        "Year" -> {
//                            filterSelect = "Year"
//                            list =
//                                ArrayList((ConvertDate.getCurrentYear() downTo 1970).map { it.toString() })
//                        }
//                        "Season" -> {
//                            filterSelect = "Season"
//                            list = UiConst.animeSeasonList.map { it.name }
//                        }
//                        "Sort" -> {
//                            filterSelect = "Sort"
//                            list = UiConst.animeSortArray
//                        }
//                        "Genre" -> {
//                            filterSelect = "Genre"
//                            list = state.genreList
//                        }
//                    }
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
                            this.putExtra(UiConst.TARGET_ID, pagingItems.get(idx)!!.id)
                            this.putExtra(
                                UiConst.TARGET_ID_MAL,
                                pagingItems[idx]!!.idMal
                            )
                        }
                    )
                }
            }
            if (state.isLoading) {
                items(9) {
//                    ItemAnimeSmallShimmer()
                }
            }
        }
    }

    if (filterDialogShow) {
        filterDialog(list, onDismiss = {
            filterDialogShow = false
        }, onClick = { selectIdx ->
            when (filterSelect) {
//                "Year" -> {
//                    if (viewModel.selectedSeason != null) {
//                        viewModel.selectType = Constant.SEASON_YEAR
//                    } else {
//                        viewModel.selectType = Constant.NO_SEASON
//                    }
//                    viewModel.selectedYear = list[selectIdx]!!.toInt()
//                    viewModel.filterList[0] =
//                        viewModel.filterList[0].copy(second = list[selectIdx]!!)
//                }
//                "Season" -> {
//                    if (viewModel.selectedYear == null) {
//                        viewModel.selectedYear = com.chs.presentation.ConvertDate.getCurrentYear(false)
//                    }
//                    viewModel.selectType = Constant.SEASON_YEAR
//                    viewModel.selectedSeason = MediaSeason.safeValueOf(list[selectIdx]!!)
//                    viewModel.filterList[1] =
//                        viewModel.filterList[1].copy(second = list[selectIdx]!!)
//                }
//                "Sort" -> {
//                    viewModel.selectedSort =
//                        MediaSort.safeValueOf(Constant.animeSortList[selectIdx].name)
//                    viewModel.filterList[2] =
//                        viewModel.filterList[2].copy(second = list[selectIdx]!!)
//                }
//                "Genre" -> {
//                    viewModel.selectGenre = list[selectIdx]
//                    viewModel.filterList[3] =
//                        viewModel.filterList[3].copy(second = list[selectIdx]!!)
//                }
            }
//            viewModel.getSortedAnime()
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
        onClick = { clickAble(subTitle) }
    ) {
        Text(text = subTitle)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun filterDialog(
    list: List<String?>,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                top = 64.dp,
                bottom = 64.dp,
                start = 16.dp,
                end = 16.dp
            ),
        onDismissRequest = onDismiss,
    ) {
        LazyColumn(
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
                    text = list[idx].toString(),
                    fontSize = 16.sp
                )
            }
        }
    }
}