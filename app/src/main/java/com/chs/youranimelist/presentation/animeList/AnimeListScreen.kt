package com.chs.youranimelist.presentation.animeList

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.util.Constant

@Composable
fun AnimeListScreen(
    searchQuery: String,
    viewModel: AnimeListViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    var searchState by mutableStateOf(searchQuery)

    LaunchedEffect(viewModel, context) {
        viewModel.getYourAnimeList()
    }

    LaunchedEffect(searchState) {
        Log.e("SearchQuery", searchState)
        viewModel.getSearchResultAnime(searchState)
    }

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
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                        this.putExtra(Constant.TARGET_ID, state.animeList[idx].animeId)
                        this.putExtra(Constant.TARGET_ID_MAL, state.animeList[idx].idMal)
                    }
                )
            }
        }
    }
}
