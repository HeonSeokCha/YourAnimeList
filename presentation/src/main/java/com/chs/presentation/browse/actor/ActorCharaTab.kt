package com.chs.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.anime.CharaImageItem
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun ActorCharaTab(
    info: Flow<PagingData<CharacterInfo>>?,
    onClick: (BrowseScreen.CharacterDetail) -> Unit
) {
    val pagingData = info?.collectAsLazyPagingItems()

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
        if (pagingData != null && pagingData.itemCount != 0) {
            items(pagingData.itemCount) {
                CharaImageItem(pagingData[it]) {
                    onClick(it)
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

                else -> Unit
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
        } else {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ItemNoResultImage()
            }
        }
    }
}