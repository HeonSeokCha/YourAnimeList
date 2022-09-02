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
import com.chs.youranimelist.presentation.browse.BrowseScreen

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel(),
    lazyListState: LazyListState,
    navController: NavController
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeRecommendList(animeId)
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        Log.e("AnimeRecScreen", screenHeight.toString())

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
            items(
                state.animeRecInfo?.animeRecommend?.recommendations?.edges?.size ?: 0
            ) { idx ->
                ItemAnimeRecommend(
                    state.animeRecInfo?.animeRecommend?.recommendations?.edges?.get(idx)?.node?.mediaRecommendation!!
                ) {
                    navController.navigate(
                        "${BrowseScreen.AnimeDetailScreen.route}/" +
                                "${state.animeRecInfo.animeRecommend.recommendations.edges[idx]?.node?.mediaRecommendation!!.id}" +
                                "/${state.animeRecInfo.animeRecommend.recommendations.edges[idx]?.node?.mediaRecommendation!!.idMal}"
                    )
                }
            }
        }
    }
}