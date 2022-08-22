package com.chs.youranimelist.presentation.search

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chs.youranimelist.presentation.browse.BrowseActivity
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
    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()
    val endOfListReached by remember {
        derivedStateOf {
            (lazyColScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    == lazyColScrollState.layoutInfo.totalItemsCount - 1)
        }
    }

    viewModel.searchPage = searchType

    LaunchedEffect(searchKeyWord) {
        if (searchKeyWord.isNotEmpty()) {
            viewModel.search(searchKeyWord)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (searchType) {
            Constant.TARGET_ANIME -> {
                items(state.searchAnimeResult.size) { idx ->
                    SearchMediaItem(state.searchAnimeResult[idx]?.animeList!!) {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                this.putExtra(
                                    Constant.TARGET_ID,
                                    state.searchAnimeResult[idx]?.animeList!!.id
                                )
                                this.putExtra(
                                    Constant.TARGET_ID_MAL,
                                    state.searchAnimeResult[idx]?.animeList!!.idMal
                                )
                            }
                        )
                    }
                }
            }

            Constant.TARGET_MANGA -> {
                items(state.searchMangaResult.size) { idx ->
                    SearchMediaItem(state.searchMangaResult[idx]?.animeList!!) {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                this.putExtra(
                                    Constant.TARGET_ID,
                                    state.searchMangaResult[idx]?.animeList!!.id
                                )
                                this.putExtra(
                                    Constant.TARGET_ID_MAL,
                                    state.searchMangaResult[idx]?.animeList!!.idMal
                                )
                            }
                        )
                    }
                }
            }

            Constant.TARGET_CHARA -> {
                items(state.searchCharaResult.size) { idx ->
                    SearchMediaItem(state.searchCharaResult[idx]!!) {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_CHARA)
                                this.putExtra(Constant.TARGET_ID, state.searchCharaResult[idx]?.id)
                            }
                        )
                    }
                }
            }
        }
    }

    if (endOfListReached) {
        if (viewModel.hasNextPage) {
            viewModel.page++
            viewModel.search(searchKeyWord)
        } else return
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