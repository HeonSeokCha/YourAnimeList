package com.chs.presentation.browse.studio

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.presentation.UiConst
import com.chs.domain.model.StudioDetailInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingAppBar
import com.chs.presentation.common.FilterDialog
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.items
import com.google.accompanist.placeholder.material.placeholder
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioDetailScreen(
    navController: NavController,
    viewModel: StudioDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity: Activity? = LocalContext.current as? Activity
    val lazyGridScrollState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val pagingItem = state.studioAnimeList?.collectAsLazyPagingItems()
    var isShowFilterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.sortOption) {
        if (state.studioId != null) {
            viewModel.getStudioAnimeList(
                state.studioId!!,
                state.sortOption.rawValue
            )
        }
    }

    Scaffold(
        topBar = {
            CollapsingAppBar(
                scrollBehavior = scrollBehavior,
                collapsingContent = {
                    StudioIndo(studioInfo = state.studioDetailInfo)
                }, toolBarClick = {
                    activity?.finish()
                },
                isShowToolBar = true
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { it ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(it),
            state = lazyGridScrollState,
            columns = StaggeredGridCells.Adaptive(100.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 4.dp
            )
        ) {
            if (state.studioDetailInfo != null) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    StudioAnimeSort(sortType = state.sortOption) {
                        isShowFilterDialog = true
                    }
                }
            }

            if (pagingItem != null) {
                items(
                    pagingItem,
                    key = { it.id }
                ) { animeInfo ->
                    if (animeInfo != null) {
                        ItemAnimeSmall(item = animeInfo) {
                            navController.navigate(
                                BrowseScreen.AnimeDetailScreen.route +
                                        "/${animeInfo.id}" +
                                        "/${animeInfo.idMal}"
                            )
                        }
                    }
                }
            }
        }

        if (isShowFilterDialog) {
            FilterDialog(
                list = UiConst.sortTypeList.map { it.name to it.rawValue },
                onClick = { idx ->
                    viewModel.changeFilterOption(UiConst.sortTypeList[idx])
                    coroutineScope.launch {
                        lazyGridScrollState.scrollToItem(0, 0)
                    }
                }, onDismiss = {
                    isShowFilterDialog = false
                }
            )
        }
    }
}

@Composable
private fun StudioIndo(studioInfo: StudioDetailInfo?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier
                .placeholder(studioInfo == null),
            text = studioInfo?.studioBasicInfo?.name ?: "Title PreView"
        )
        Row(
            modifier = Modifier
                .placeholder(studioInfo == null)
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
            Text(
                text = "${studioInfo?.favourites ?: 0}"
            )
        }
    }
}


@Composable
private fun StudioAnimeSort(
    sortType: UiConst.SortType,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Sort : "
        )

        Text(
            modifier = Modifier
                .clickable { onClick() },
            text = sortType.name
        )
    }
}