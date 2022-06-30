package com.chs.youranimelist.presentation.sortList

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.presentation.browse.BrowswActivity
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.ConvertDate
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SortedListScreen(
    navigator: DestinationsNavigator,
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
        }
        Constant.POPULAR_THIS_SEASON -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectedSeason = ConvertDate.getCurrentSeason()
            viewModel.selectedYear = ConvertDate.getCurrentYear(false)
            viewModel.selectType = Constant.SEASON_YEAR
        }
        Constant.UPCOMING_NEXT_SEASON -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectedSeason = ConvertDate.getNextSeason()
            viewModel.selectedYear = ConvertDate.getCurrentYear(true)
            viewModel.selectType = Constant.SEASON_YEAR
        }
        Constant.ALL_TIME_POPULAR -> {
            viewModel.selectedSort = MediaSort.POPULARITY_DESC
            viewModel.selectType = Constant.NO_SEASON_NO_YEAR
        }
    }

    LaunchedEffect(viewModel, context) {
        viewModel.getSortedAnime()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Year")
            Text(text = "Season")
            Text(text = "Sort")
            Text(text = "Genre")
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
                            Intent(context, BrowswActivity::class.java)
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