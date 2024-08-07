package com.chs.presentation.animeList

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage

@Composable
fun AnimeListScreen(
    state: AnimeListState,
    onStartActivity: (Int, Int) -> Unit
) {


    if (state.animeList.isEmpty()) {
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
                state.animeList.size,
                key = { state.animeList[it].id }
            ) { idx ->
                ItemAnimeLarge(anime = state.animeList[idx]) {
                    onStartActivity(state.animeList[idx].id, state.animeList[idx].idMal)
                }
            }
        }
    }
}
