package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnimeRecScreen(
    animeId: Int,
    viewModel: AnimeRecViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeRecommendList(animeId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .height(
//                ((state.animeRecInfo?.animeRecommend?.recommendations?.edges?.size
//                    ?: 200) * 204).dp
//            )
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            state.animeRecInfo?.animeRecommend?.recommendations?.edges?.size ?: 0
        ) { idx ->
            ItemAnimeRecommend(
                state.animeRecInfo?.animeRecommend?.recommendations?.edges?.get(idx)?.node?.mediaRecommendation!!
            )
        }
    }
}