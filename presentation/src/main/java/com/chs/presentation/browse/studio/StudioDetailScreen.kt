package com.chs.presentation.browse.studio

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.common.UiConst
import com.chs.presentation.common.ItemAnimeSmall


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudioDetailScreen(
    studioId: Int,
    viewModel: StudioDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current
    val activity: Activity? = LocalContext.current as? Activity

    val pagingItem = state.studioAnimeList?.collectAsLazyPagingItems()

    LaunchedEffect(viewModel, context) {
        viewModel.getStudioDetailInfo(studioId)
        viewModel.getStudioAnimeList(studioId, UiConst.SortType.POPULARITY.rawValue)
    }


    LazyVerticalStaggeredGrid(
        modifier = Modifier,
        columns = StaggeredGridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp
    ) {
        if (state.studioDetailInfo != null) {
            item(span = StaggeredGridItemSpan.FullLine) {
                StudioAnimeSort(sort = state.sortOption) {

                }
            }
        }

        if (pagingItem != null) {
        }
    }
}


@Composable
private fun StudioAnimeSort(
    sort: UiConst.SortType,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Sort : "
        )

        Text(
            modifier = Modifier
                .clickable { onClick() },
            text = sort.rawValue
        )
    }
}