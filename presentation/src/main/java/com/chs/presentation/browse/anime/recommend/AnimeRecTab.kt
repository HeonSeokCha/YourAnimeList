package com.chs.presentation.browse.anime.recommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseScreen

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(context, viewModel) {
        viewModel.getAnimeRecommendList(animeId)
    }

    val lazyPagingItems = state.animeRecInfo?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .height(1020.dp)
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (lazyPagingItems != null) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey(key = { it.id }),
                contentType = lazyPagingItems.itemContentType()
            ) { index ->
                val item = lazyPagingItems[index]
                if (item != null) {
                    ItemAnimeRecommend(item) {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${item.id}" +
                                    "/${item.idMal}"
                        )
                    }
                }
            }
        }
    }

    if (lazyPagingItems?.loadState?.append == LoadState.Loading) {
        LoadingIndicator()
    }
}