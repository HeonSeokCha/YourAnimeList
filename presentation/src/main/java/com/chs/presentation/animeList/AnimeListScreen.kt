package com.chs.presentation.animeList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage

@Composable
fun AnimeListScreen(
    list: List<AnimeInfo>,
    onStartActivity: (Int, Int) -> Unit
) {
    if (list.isEmpty()) {
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
            items(
                list.size,
                key = { list[it].id }
            ) { idx ->
                ItemAnimeLarge(anime = list[idx]) {
                    onStartActivity(list[idx].id, list[idx].idMal)
                }
            }
        }
    }
}
