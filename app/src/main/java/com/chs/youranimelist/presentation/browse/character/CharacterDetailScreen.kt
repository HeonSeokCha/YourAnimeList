package com.chs.youranimelist.presentation.browse.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var expandDesc by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, context) {
        viewModel.getCharacterDetail(charaId)
        viewModel.isSaveCharacter(charaId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            ),
    ) {
        val characterInfo = state.characterDetailInfo?.character

        CharacterBanner(state)

        Button(
            modifier = Modifier
                .fillMaxWidth(),
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

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        if (expandDesc) {
            Text(
                text = state.characterDetailInfo?.character?.description ?: "",
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    expandDesc = false
                }) {
                Icon(imageVector = Icons.Filled.ArrowDropUp, contentDescription = null)
            }
        } else {
            Text(
                text = state.characterDetailInfo?.character?.description ?: "",
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    expandDesc = true
                }) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.Center)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = characterInfo?.name?.full.toString())
                Text(text = characterInfo?.name?.native.toString())
                Row {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(text = characterInfo?.favourites.toString())
                }

            }
        }
    }
}