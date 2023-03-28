package com.chs.presentation.animeList

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.presentation.browse.BrowseActivity
import com.chs.common.UiConst

@Composable
fun AnimeListScreen(
    searchQuery: String,
    viewModel: AnimeListViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getYourAnimeList()
    }

//    LaunchedEffect(searchQuery) {
//        viewModel.getSearchResultAnime(searchQuery)
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(state.animeList.size) { idx ->
            ItemYourAnime(anime = state.animeList[idx]) {
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
