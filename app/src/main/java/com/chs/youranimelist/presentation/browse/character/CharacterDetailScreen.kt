package com.chs.youranimelist.presentation.browse.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.util.color

@Composable
fun CharacterDetailScreen(
    charaId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getCharacterDetail(charaId)
        viewModel.isSaveCharacter(charaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val characterInfo = state.characterDetailInfo?.character

        CharacterBanner(state)

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            onClick = {
                if (state.isSaveChara != null) {
                    viewModel.deleteCharacter()
                } else {
                    viewModel.insertCharacter()
                }
            }
        ) {
            if (state.isSaveChara != null) {
                Text("SAVED")
            } else {
                Text("ADD MY LIST")
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            columns = GridCells.Adaptive(100.dp),
        ) {
            items(characterInfo?.media?.edges?.size ?: 0) { idx ->
                ItemAnimeSmall(
                    item = characterInfo?.media?.edges?.get(idx)?.node?.animeList!!,
                    onClick = {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${characterInfo.media.edges[idx]?.node?.animeList!!.id}" +
                                    "/${characterInfo.media.edges[idx]?.node?.animeList!!.idMal}"
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun CharacterBanner(
    state: CharacterDetailState,
) {
    val characterInfo = state.characterDetailInfo?.character
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(100))
                    .background(
                        color = "#ffffff".color
                    ),
                model = characterInfo?.image?.large,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(
                    end = 8.dp
                )
            ) {
                Text(text = characterInfo?.name?.full.toString())
            }
        }
    }
}