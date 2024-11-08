package com.chs.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.anime.CharaImageItem
import kotlinx.coroutines.flow.Flow

@Composable
fun ActorCharaTab(
    info: Flow<PagingData<CharacterInfo>>?,
    onClick: (BrowseScreen.CharacterDetailScreen) -> Unit
) {
    val pagingData = info?.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        columns = GridCells.Fixed(3)
    ) {
        if (pagingData != null) {
            items(pagingData.itemCount) {
                CharaImageItem(pagingData[it]) {
                    onClick(it)
                }
            }
        }
    }
}