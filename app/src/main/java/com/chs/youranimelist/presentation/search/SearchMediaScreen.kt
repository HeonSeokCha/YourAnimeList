package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.presentation.animeList.ItemYourAnime
import com.chs.youranimelist.presentation.charaList.ItemYourChara
import com.chs.youranimelist.ui.theme.Pink80
import com.chs.youranimelist.util.Constant
import kotlinx.coroutines.delay

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel()
) {
    val state = viewModel.state

    viewModel.searchPage = searchType

    LaunchedEffect(searchKeyWord) {
        delay(3000L)
        if (searchKeyWord.isNotEmpty()) {
            viewModel.search(searchKeyWord)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (searchType) {
            Constant.TARGET_ANIME -> {
                items(state.searchAnimeResult.size) { idx ->
                    SearchMediaItem(state.searchAnimeResult[idx]?.animeList!!) {

                    }
                }
            }

            Constant.TARGET_MANGA -> {
                items(state.searchMangaResult.size) { idx ->
                    SearchMediaItem(state.searchMangaResult[idx]?.animeList!!) {

                    }
                }
            }

            Constant.TARGET_CHARA -> {
                items(state.searchCharaResult.size) { idx ->
                    SearchMediaItem(state.searchCharaResult[idx]!!) {

                    }
                }
            }
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Pink80)
        }
    }
}