package com.chs.youranimelist.presentation.browse.anime

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun AnimeCharaScreen(
    animeId: Int,
    viewModel: AnimeCharaViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lazyGridScrollState = rememberLazyGridState()

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeCharacters(animeId)
    }
    if (state.isError.isNotEmpty()) {
        Toast.makeText(context, state.isError, Toast.LENGTH_SHORT).show()
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(1000.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        state = lazyGridScrollState,
        columns = GridCells.Fixed(3),
    ) {
        items(state.animeCharaInfo?.media?.characters?.charactersNode?.size ?: 0) { idx ->
            Column(
                modifier = Modifier
                    .width(100.dp)

            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100)),
                    model = state.animeCharaInfo?.media?.characters?.charactersNode?.get(idx)?.image?.large
                        ?: "",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                )
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = state.animeCharaInfo?.media?.characters?.charactersNode?.get(idx)?.name?.full.toString()
                )
            }
        }
    }
}