package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.chs.youranimelist.AnimeDetailQuery

@Composable
fun AnimeDetailScreen(
    id: Int,
    idMal: Int,
    viewModel: AnimeDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeDetailInfo(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DetailBanner(state.animeDetailInfo)
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Magenta)
        }
    }
}

@Composable
fun DetailBanner(animeInfo: AnimeDetailQuery.Data?) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        model = animeInfo?.media?.bannerImage,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        model = animeInfo?.media?.coverImage?.extraLarge,
        contentDescription = null,
    )
}



