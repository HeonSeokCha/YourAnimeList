package com.chs.presentation.browse.anime.recommend

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var placeItemShow by remember { mutableStateOf(false) }
    var isEmptyShow by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    LaunchedEffect(context, viewModel) {
        viewModel.getAnimeRecommendList(animeId)
    }

    val lazyPagingItems = state.animeRecInfo?.collectAsLazyPagingItems()

    if (isEmptyShow) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (lazyPagingItems != null) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey(key = { it.id }),
                    contentType = lazyPagingItems.itemContentType()
                ) { index ->
                    val item = lazyPagingItems[index]
                    ItemAnimeLarge(item) {
                        if (item != null) {
                            navController.navigate(
                                "${BrowseScreen.AnimeDetailScreen.route}/" +
                                        "${item.id}" +
                                        "/${item.idMal}"
                            )
                        }
                    }
                }

                if (placeItemShow) {
                    items(6) {
                        ItemAnimeLarge(null) { }
                    }
                }
            }
        }
    }


    if (lazyPagingItems != null) {
        placeItemShow = when (lazyPagingItems.loadState.source.refresh) {
            is LoadState.Loading -> true
            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> {
                Log.e("Lazy refresh", lazyPagingItems.itemCount.toString())
                isEmptyShow = lazyPagingItems.itemCount == 0
                lazyPagingItems.itemCount < 0
            }
        }
    }
}