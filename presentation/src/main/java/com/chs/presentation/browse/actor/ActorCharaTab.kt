package com.chs.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.anime.CharaImageItem
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun ActorCharaTab(
    info: Flow<PagingData<CharacterInfo>>,
    onCharaClick: (Int) -> Unit
) {
    val pagingData = info.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        columns = GridCells.Fixed(3)
    ) {
        items(
            count = pagingData.itemCount,
            key = pagingData.itemKey { it.id }
        ) {
            CharaImageItem(pagingData[it]) { charaInfo ->
                onCharaClick(charaInfo.id)
            }
        }


        when (pagingData.loadState.refresh) {
            is LoadState.Loading -> {
                items(10) {
                    CharaImageItem(null) { }
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
                    CharaImageItem(null) { }
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