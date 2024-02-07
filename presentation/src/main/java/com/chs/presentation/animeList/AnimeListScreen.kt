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
    viewModel: AnimeListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getYourAnimeList()
    }

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
                    context.startActivity(
                        Intent(
                            context, BrowseActivity::class.java
                        ).apply {
                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                            this.putExtra(UiConst.TARGET_ID, state.animeList[idx].id)
                            this.putExtra(UiConst.TARGET_ID_MAL, state.animeList[idx].idMal)
                        }
                    )
                }
            }
        }
    }
}
