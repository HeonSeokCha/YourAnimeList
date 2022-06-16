package com.chs.youranimelist.presentation.animeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chs.youranimelist.domain.model.Anime
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AnimeListScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "AnimeListScreen")
    }
}

@Composable
fun ItemAnimeList(anime: Anime) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(anime.coverImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text()
            Text()
            Text()
            Row {
                Icon()
                Text()
                Icon()
                Text()
            }
            LazyRow()
        }
    }
}