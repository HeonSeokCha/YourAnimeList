package com.chs.presentation.browse.studio

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.model.StudioDetailInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.sortList.ItemExpandSingleBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StudioDetailScreen(
    state: StudioDetailState,
    onEvent: (StudioEvent) -> Unit,
    onNavigate: (BrowseScreen.AnimeDetailScreen) -> Unit
) {
    val activity: Activity? = LocalContext.current as? Activity
    val lazyGridScrollState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val pagingItem = state.studioAnimeList?.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                scrollState.scrollTo(0)
                onEvent(StudioEvent.GetStudioInfo)
                delay(500L)
                isRefreshing = false
            }
        }
    ) {
        CollapsingToolbarScaffold(
            scrollState = scrollState,
            header = {
                when (state.studioDetailInfo) {
                    is Resource.Loading -> {
                        StudioInfo(studioInfo = null)
                    }

                    is Resource.Success -> {
                        StudioInfo(studioInfo = state.studioDetailInfo.data)
                    }

                    is Resource.Error -> {}

                }
            }, onCloseClick = {
                activity?.finish()
            }
        ) {
            LazyVerticalStaggeredGrid(
                state = lazyGridScrollState,
                columns = StaggeredGridCells.Adaptive(100.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp,
                contentPadding = PaddingValues(
                    horizontal = 4.dp,
                    vertical = 4.dp
                )
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Column {
                        ItemExpandSingleBox(
                            title = "Sort",
                            list = UiConst.sortTypeList,
                            initValue = state.sortOption.first
                        ) { selectValue ->
                            coroutineScope.launch {
                                lazyGridScrollState.scrollToItem(0, 0)
                            }
                            onEvent(StudioEvent.ChangeSortOption(selectValue!!))
                        }
                    }
                }

                if (pagingItem != null) {
                    items(
                        count = pagingItem.itemCount,
                        key = pagingItem.itemKey { it.id }
                    ) {
                        val animeInfo = pagingItem[it]
                        if (animeInfo != null) {
                            ItemAnimeSmall(item = animeInfo) {
                                onNavigate(
                                    BrowseScreen.AnimeDetailScreen(
                                        id = animeInfo.id,
                                        idMal = animeInfo.idMal
                                    )
                                )
                            }
                        }
                    }

                    when (pagingItem.loadState.refresh) {
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


                    when (pagingItem.loadState.append) {
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
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun StudioInfo(studioInfo: StudioDetailInfo?) {
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
            text = studioInfo?.studioBasicInfo?.name ?: "Title PreView"
        )
        Row {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
            Text(
                text = String.format("%,d", studioInfo?.favourites ?: 0)
            )
        }
    }
}