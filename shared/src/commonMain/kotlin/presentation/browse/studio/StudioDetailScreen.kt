package presentation.browse.studio

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import presentation.UiConst
import com.chs.domain.model.StudioDetailInfo
import presentation.browse.CollapsingLayout
import presentation.common.ItemAnimeSmall
import presentation.common.ItemExpandSingleBox
import presentation.toCommaFormat
import kotlinx.coroutines.launch

@Composable
fun StudioDetailScreenRoot(
    viewModel: StudioDetailViewModel,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val studioEvent by viewModel.event.collectAsStateWithLifecycle(StudioDetailEvent.Idle)

    val context = LocalContext.current

    LaunchedEffect(studioEvent) {
        when (studioEvent) {
            StudioDetailEvent.OnError -> {
                Toast.makeText(context, "Something error in load Data..", Toast.LENGTH_SHORT).show()
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

    if (pagingItem != null) {
        when (pagingItem.loadState.refresh) {

            is LoadState.Error -> {
                onEvent(StudioDetailEvent.OnError)
            }

            else -> Unit
        }

        when (pagingItem.loadState.append) {
            is LoadState.Loading -> {
                isAppending = true
            }

            is LoadState.Error -> {
                onEvent(StudioDetailEvent.OnError)
                isAppending = false
            }

            else -> isAppending = false
        }
    }

    CollapsingLayout(
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
            Text(text = studioInfo?.favourites.toCommaFormat)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudioScreen() {
    StudioDetailScreen(state = StudioDetailState()) { }
}