package com.chs.youranimelist.presentation.browse.anime

import android.content.Intent
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
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.util.Constant

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