package com.chs.youranimelist.presentation.browse.anime

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.browse.character.CharacterDetailScreen

@Composable
fun AnimeCharaScreen(
    animeId: Int,
    viewModel: AnimeCharaViewModel = hiltViewModel(),
    lazyGridScrollState: LazyGridState,
    navController: NavController
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeCharacters(animeId)
    }
    if (state.isError.isNotEmpty()) {
        Toast.makeText(context, state.isError, Toast.LENGTH_SHORT).show()
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
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
                    .clickable {
                        navController.navigate(
                            "${BrowseScreen.CharacterDetailScreen.route}/" +
                                    "${
                                        state.animeCharaInfo?.media?.characters?.charactersNode?.get(
                                            idx
                                        )?.id ?: 0
                                    }"
                        )
                    }
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
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = state.animeCharaInfo?.media?.characters?.charactersNode?.get(idx)?.name?.full.toString()
                )
            }
        }
    }
}