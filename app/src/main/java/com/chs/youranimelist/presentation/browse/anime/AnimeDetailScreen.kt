package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.presentation.browse.BrowseScreen

@Composable
fun AnimeDetailScreen(
    id: Int,
    idMal: Int,
    navController: NavController,
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

        Button(
            onClick = {
                navController.navigate(
                    "${BrowseScreen.AnimeDetailScreen.route}/$id/$idMal"
                )
            }
        ) {
            Text("AnimeDetail")
        }

        Button(
            onClick = {
                navController.navigate(
                    "${BrowseScreen.CharacterDetailScreen.route}/$id"
                )
            }
        ) {
            Text("Character")
        }

        Button(
            onClick = {
                navController.navigate(
                    "${BrowseScreen.StudioDetailScreen.route}/$id"
                )
            }
        ) {
            Text("Studio")
        }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = animeInfo?.media?.bannerImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.Center),
            onClick = { /*TODO*/ }
        ) {
            Icon(Icons.Filled.PlayArrow, null)
        }
        AsyncImage(
            modifier = Modifier
                .width(130.dp)
                .height(180.dp)
                .padding(
                    start = 8.dp,

                )
                .align(Alignment.BottomStart),
            model = animeInfo?.media?.coverImage?.extraLarge,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 154.dp)
        ) {
            Text(
                text = animeInfo?.media?.title?.english
                    ?: animeInfo?.media?.title?.romaji.toString()
            )

            Text(text = animeInfo?.media?.format?.name ?: "")

            Row {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = animeInfo?.media?.averageScore.toString(),
                    fontWeight = FontWeight.Bold,

                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = animeInfo?.media?.favourites.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}



