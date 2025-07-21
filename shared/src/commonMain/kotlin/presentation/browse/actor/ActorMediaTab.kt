package presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import presentation.UiConst
import presentation.common.ItemActorMedia
import presentation.common.ItemAnimeSmall
import presentation.common.ItemExpandSingleBox
import presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ActorMediaTab(
    info: Flow<PagingData<Pair<CharacterInfo, AnimeInfo>>>?,
    sortOptionName: String,
    onAnimeClick: (id: Int, idMal: Int) -> Unit,
    onCharaClick: (id: Int) -> Unit,
    onChangeSortEvent: (String) -> Unit
) {
    val listState = rememberLazyGridState()
    val pagingData = info?.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        state = listState,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 8.dp
        )
    ) {
        item(span = { GridItemSpan(3) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                ItemExpandSingleBox(
                    title = "Sort",
                    list = UiConst.sortTypeList2,
                    initValue = sortOptionName,
                ) { selectValue ->
                    if (selectValue != null) {
                        coroutineScope.launch {
                            listState.scrollToItem(0, 0)
                        }
                        onChangeSortEvent(selectValue.second)
                    }
                }
            }
        }

        if (pagingData != null) {
            items(
                count = pagingData.itemCount,
                key = pagingData.itemKey { "${it.first.id}/${it.second.id}" }
            ) {
                ItemActorMedia(
                    info = pagingData[it],
                    onAnimeClick = { id, idMal ->
                        onAnimeClick(id, idMal)
                    },
                    onCharaClick = { id ->
                        onCharaClick(id)
                    }
                )
            }

            when (pagingData.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemAnimeSmall(null)
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> {
                    if (pagingData.itemCount == 0) {
                        item {
                            ItemNoResultImage()
                        }
                    }
                }
            }

            when (pagingData.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemAnimeSmall(null)
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