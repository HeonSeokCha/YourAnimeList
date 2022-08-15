package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.presentation.animeList.ItemYourAnime
import com.chs.youranimelist.presentation.charaList.ItemYourChara
import com.chs.youranimelist.util.Constant

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    viewModel.searchPage = searchType

    LaunchedEffect(searchKeyWord) {
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
}