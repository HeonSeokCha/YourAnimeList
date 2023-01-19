package com.chs.youranimelist.presentation.browse.anime

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.youranimelist.presentation.LoadingIndicator
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmallShimmer

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state
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
        items(lazyPagingItems?.itemCount ?: 0) { idx ->
            lazyPagingItems?.get(idx)?.node?.mediaRecommendation?.let {
                ItemAnimeRecommend(it) {
                    navController.navigate(
                        "${BrowseScreen.AnimeDetailScreen.route}/" +
                                "${it.id}" +
                                "/${it.idMal}"
                    )
                }
            }
        }

        if (lazyPagingItems?.loadState?.source?.refresh == LoadState.Loading) {
            items(6) {
                ItemAnimeRecShimmer()
            }
        }
    }

//    when (lazyPagingItems?.loadState?.source?.refresh) {
//        is LoadState.Loading -> {
//            LoadingIndicator()
//        }
//
//        is LoadState.Error -> {
//            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
//        }
//        else -> {}
//    }

}