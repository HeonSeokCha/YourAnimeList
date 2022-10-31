package com.chs.youranimelist.presentation.browse.anime

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.ui.theme.Pink80

@Composable
fun AnimeRecScreen(
    context: Context,
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    navController: NavController
) {

    val lazyPagingItems = viewModel.getAnimeRecommendList(animeId).collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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

    when (lazyPagingItems.loadState.source.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Pink80)
            }
        }

        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

}