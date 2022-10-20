package com.chs.youranimelist.presentation.browse.anime

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.youranimelist.presentation.browse.BrowseScreen

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    navController: NavController
) {

    val lazyPagingItems = viewModel.getAnimeRecommendList(animeId).collectAsLazyPagingItems()

    BoxWithConstraints {

        LazyColumn(
            modifier = Modifier
                .height(1000.dp)
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = lazyListState
        ) {
            items(lazyPagingItems) { recommendMedia ->
                recommendMedia?.node?.mediaRecommendation?.let {
                    ItemAnimeRecommend(it) {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${it.id}" +
                                    "/${it.idMal}"
                        )
                    }
                }
            }
        }
    }
}