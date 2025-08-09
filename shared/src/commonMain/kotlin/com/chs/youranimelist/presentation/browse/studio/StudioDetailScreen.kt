package com.chs.youranimelist.presentation.browse.studio

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
import androidx.compose.runtime.LaunchedEffect
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
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.domain.model.StudioDetailInfo
import com.chs.youranimelist.presentation.common.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.common.ItemExpandSingleBox
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
    val studioEvent by viewModel.event.collectAsStateWithLifecycle(StudioDetailEvent.Idle)


    LaunchedEffect(studioEvent) {
        when (studioEvent) {
            StudioDetailEvent.OnError -> {
            }

            else -> Unit
        }
    }

    StudioDetailScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                StudioDetailEvent.ClickBtn.Close -> {
                    onCloseClick()
                }

                is StudioDetailEvent.ClickBtn.Anime -> {
                    onAnimeClick(event.id, event.idMal)
                }

                else -> viewModel.changeEvent(event)
            }
        }
    )
}

@Composable
fun StudioDetailScreen(
    state: StudioDetailState,
    onEvent: (StudioDetailEvent) -> Unit,
) {
    val lazyGridScrollState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()
    val pagingItem = state.studioAnimeList?.collectAsLazyPagingItems()
    var isAppending by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if (pagingItem != null) {
        when (pagingItem.loadState.refresh) {

            is LoadStateError -> {
                onEvent(StudioDetailEvent.OnError)
            }

            else -> Unit
        }

        when (pagingItem.loadState.append) {
            is LoadStateLoading -> {
                isAppending = true
            }

            is LoadStateError -> {
                onEvent(StudioDetailEvent.OnError)
                isAppending = false
            }

            else -> isAppending = false
        }
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            StudioInfo(studioInfo = state.studioDetailInfo)
        },
        isShowTopBar = true,
        onCloseClick = {
            onEvent(StudioDetailEvent.ClickBtn.Close)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    ItemExpandSingleBox(
                        title = "Sort",
                        list = UiConst.sortTypeList,
                        initValue = state.sortOption.first
                    ) { selectValue ->
                        coroutineScope.launch {
                            lazyGridScrollState.scrollToItem(0, 0)
                        }
                        onEvent(StudioDetailEvent.ClickBtn.SortOption(selectValue!!))
                    }
                }
            }

            if (state.isLoading) {
                items(10) {
                    ItemAnimeSmall(item = null) { }
                }
            } else {
                items(count = pagingItem!!.itemCount) {
                    val animeInfo = pagingItem[it]
                    if (animeInfo != null) {
                        ItemAnimeSmall(item = animeInfo) {
                            onEvent(
                                StudioDetailEvent.ClickBtn.Anime(
                                    id = animeInfo.id,
                                    idMal = animeInfo.idMal
                                )
                            )
                        }
                    }
                }

                if (isAppending) {
                    items(6) {
                        ItemAnimeSmall(item = null) { }
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
            Text(text = studioInfo?.favourites.toCommaFormat())
        }
    }
}

@Preview()
@Composable
private fun PreviewStudioScreen() {
    StudioDetailScreen(state = StudioDetailState()) { }
}