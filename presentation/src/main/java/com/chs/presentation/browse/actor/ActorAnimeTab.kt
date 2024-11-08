package com.chs.presentation.browse.actor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.ItemAnimeSmall
import kotlinx.coroutines.flow.Flow

@Composable
fun ActorAnimeTab(
    info: Flow<PagingData<AnimeInfo>>?,
    onClick: (BrowseScreen.AnimeDetailScreen) -> Unit
) {
    val listState = rememberLazyGridState()
    val pagingData = info?.collectAsLazyPagingItems()

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
        if (pagingData != null) {
            items(pagingData.itemCount) {
                ItemAnimeSmall(pagingData[it]) {
                    onClick(
                        BrowseScreen.AnimeDetailScreen(
                            id = pagingData[it]!!.id,
                            idMal = pagingData[it]!!.idMal
                        )
                    )
                }
            }
        }
    }
}