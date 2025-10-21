package com.chs.youranimelist.presentation.browse.studio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.domain.model.StudioDetailInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.common.ItemExpandSingleBox
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import com.chs.youranimelist.presentation.common.shimmer
import com.chs.youranimelist.presentation.sortList.SortIntent
import com.chs.youranimelist.presentation.toCommaFormat
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StudioDetailScreenRoot(
    viewModel: StudioDetailViewModel,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudioDetailEffect.NavigateAnimeDetail -> onAnimeClick(effect.id, effect.idMal)
                StudioDetailEffect.OnClose -> onCloseClick()
            }
        }
    }

    StudioDetailScreen(
        state = state,
        pagingItem = pagingItems,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun StudioDetailScreen(
    state: StudioDetailState,
    pagingItem: LazyPagingItems<AnimeInfo>,
    onIntent: (StudioDetailIntent) -> Unit,
) {
    val lazyGridScrollState = rememberLazyStaggeredGridState()
    val scrollState = rememberScrollState()


    val isEmpty by remember {
        derivedStateOf {
            pagingItem.loadState.refresh is LoadState.NotLoading
                    && pagingItem.loadState.append.endOfPaginationReached
                    && pagingItem.itemCount == 0
        }
    }

    LaunchedEffect(pagingItem.loadState.refresh) {
        when (pagingItem.loadState.refresh) {
            is LoadState.Loading -> {
                lazyGridScrollState.scrollToItem(0, 0)
                onIntent(StudioDetailIntent.OnLoading)
            }

            is LoadState.Error -> {
                (pagingItem.loadState.refresh as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(StudioDetailIntent.OnLoadComplete)
        }
    }

    LaunchedEffect(pagingItem.loadState.append) {
        when (pagingItem.loadState.append) {
            is LoadState.Loading -> {
                onIntent(StudioDetailIntent.OnAppendLoading)
            }

            is LoadState.Error -> {
                (pagingItem.loadState.append as LoadState.Error).error.run {
                }
            }

            is LoadState.NotLoading -> onIntent(StudioDetailIntent.OnAppendLoadComplete)
        }
    }


    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            StudioInfo(studioInfo = state.studioDetailInfo)
        },
        isShowTopBar = true,
        onCloseClick = { onIntent(StudioDetailIntent.ClickClose) }
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(state.studioDetailInfo == null),
                    horizontalAlignment = Alignment.End
                ) {
                    ItemExpandSingleBox(
                        title = "Sort",
                        list = SortType.entries.toList(),
                        initValue = state.sortOption
                    ) { selectValue ->
                        if (selectValue == null) return@ItemExpandSingleBox
                        onIntent(StudioDetailIntent.ClickSortOption(selectValue))
                    }
                }
            }

            when {
                isEmpty -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        ItemNoResultImage(modifier = Modifier.fillMaxSize())
                    }
                }

                state.isPagingLoading -> {
                    items(UiConst.BANNER_SIZE) {
                        ItemAnimeSmall(item = null) { }
                    }
                }

                else -> {
                    items(count = pagingItem.itemCount) {
                        val animeInfo = pagingItem[it]
                        if (animeInfo != null) {
                            ItemAnimeSmall(item = animeInfo) {
                                onIntent(
                                    StudioDetailIntent.ClickAnime(
                                        id = animeInfo.id,
                                        idMal = animeInfo.idMal
                                    )
                                )
                            }
                        }
                    }

                    if (state.isPagingAppendLoading) {
                        items(UiConst.BANNER_SIZE) {
                            ItemAnimeSmall(item = null) { }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StudioInfo(studioInfo: StudioDetailInfo?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            modifier = Modifier.shimmer(studioInfo == null),
            text = studioInfo?.studioBasicInfo?.name ?: "Title PreView"
        )
        Row(
            modifier = Modifier.shimmer(studioInfo == null),
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
            Text(text = studioInfo?.favourites.toCommaFormat())
        }
    }
}