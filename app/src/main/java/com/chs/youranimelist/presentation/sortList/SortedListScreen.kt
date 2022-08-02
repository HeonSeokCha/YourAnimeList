package com.chs.youranimelist.presentation.sortList

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.ConvertDate

@Composable
fun SortedListScreen(
    sortType: String,
    viewModel: SortedViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()

    when (sortType) {
        Constant.TRENDING_NOW -> {
            viewModel.selectedSort = MediaSort.TRENDING_DESC
            viewModel.selectType = Constant.NO_SEASON_NO_YEAR
            viewModel.filterList[2] = viewModel.filterList[2].copy(second = "Trending")
        }
        Constant.POPULAR_THIS_SEASON -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectedSeason = ConvertDate.getCurrentSeason()
            viewModel.selectedYear = ConvertDate.getCurrentYear(false)
            viewModel.selectType = Constant.SEASON_YEAR
            viewModel.filterList[0] =
                viewModel.filterList[0].copy(second = ConvertDate.getCurrentYear(false).toString())
            viewModel.filterList[1] =
                viewModel.filterList[1].copy(second = ConvertDate.getCurrentSeason().toString())
            viewModel.filterList[2] = viewModel.filterList[2].copy(second = "Popularity")
        }
        Constant.UPCOMING_NEXT_SEASON -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectedSeason = ConvertDate.getNextSeason()
            viewModel.selectedYear = ConvertDate.getCurrentYear(true)
            viewModel.selectType = Constant.SEASON_YEAR

            viewModel.filterList[0] =
                viewModel.filterList[0].copy(second = ConvertDate.getCurrentYear(true).toString())
            viewModel.filterList[1] =
                viewModel.filterList[1].copy(second = ConvertDate.getNextSeason().toString())
            viewModel.filterList[2] = viewModel.filterList[2].copy(second = "Popularity")
        }
        Constant.ALL_TIME_POPULAR -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectType = Constant.NO_SEASON_NO_YEAR
            viewModel.filterList[2] = viewModel.filterList[2].copy(second = "Popularity")
        }
    }

    LaunchedEffect(viewModel, context) {
        viewModel.getSortedAnime()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(viewModel.filterList.size) { idx ->
                ItemSort(
                    title = viewModel.filterList[idx].first,
                    subTitle = viewModel.filterList[idx].second
                ) {
                    // TODO: open Dialog to items..
                }
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            state = lazyGridScrollState,
            columns = GridCells.Adaptive(100.dp),
        ) {
            items(state.animeSortList.size) {
                ItemAnimeSmall(
                    item = state.animeSortList[it],
                    onClick = {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                this.putExtra(Constant.TARGET_ID, state.animeSortList[it].id)
                                this.putExtra(Constant.TARGET_ID_MAL, state.animeSortList[it].idMal)
                            }
                        )
                    }
                )
            }
        }
    }

    if (lazyGridScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        == lazyGridScrollState.layoutInfo.totalItemsCount - 1
    ) {
        if (viewModel.hasNextPage) {
            viewModel.page++
            viewModel.getSortedAnime()
        } else {
            return
        }
    }


    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Magenta)
        }
    }
}

@Composable
fun ItemSort(
    title: String,
    subTitle: String,
    clickAble: () -> Unit
) {
    Text(
        text = title
    )
    TextButton(onClick = { clickAble() }) {
        Text(text = subTitle)
    }
}