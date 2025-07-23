package com.chs.youranimelist.presentation.animeList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.common.ItemNoResultImage
import com.chs.youranimelist.presentation.common.ItemAnimeLarge
import com.chs.youranimelist.presentation.common.ItemNoResultImage

@Composable
fun AnimeListScreen(
    state: AnimeListState,
    onStartActivity: (Int, Int) -> Unit
) {
    if (!state.isLoading && state.list.isEmpty()) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                vertical = 4.dp
            )
        ) {
            if (state.isLoading) {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            } else {
                items(
                    items = state.list,
                    key = { it.id }
                ) { animeInfo ->
                    ItemAnimeLarge(anime = animeInfo) {
                        onStartActivity(animeInfo.id, animeInfo.idMal)
                    }
                }
            }
        }
    }
}
