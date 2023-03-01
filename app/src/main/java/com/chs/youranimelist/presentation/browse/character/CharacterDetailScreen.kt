package com.chs.youranimelist.presentation.browse.character

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.LoadingIndicator
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.util.color

@OptIn(ExperimentalFoundationApi::class)
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

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            CharacterBanner(
                state = state,
                insertClick = {
                    viewModel.insertCharacter()
                }, deleteClick = {
                    viewModel.deleteCharacter()
                }
            )
        }
        if (state.characterDetailInfo?.animeList != null) {
            items(state.characterDetailInfo.animeList) { anime ->
                ItemAnimeSmall(
                    item = anime,
                    onClick = {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${anime.id}" +
                                    "/${anime.idMal}"
                        )
                    }
                )
            }
        }
    }

    if (state.isLoading) {
        LoadingIndicator()
    }
}


@Composable
fun CharacterBanner(
    state: CharacterDetailState,
    insertClick: () -> Unit,
    deleteClick: () -> Unit
) {
    val characterInfo = state.characterDetailInfo
    val activity = (LocalContext.current as? Activity)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 60.dp,
                    start = 8.dp,
                    end = 8.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(100))
                        .background(
                            color = "#ffffff".color
                        ),
                    model = characterInfo?.characterInfo?.imageUrl,
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
                    Text(text = characterInfo?.characterInfo?.name ?: "")
                    Text(text = characterInfo?.characterInfo?.nativeName ?: "")
                    Row {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        Text(text = characterInfo?.favorites.toString())
                    }

                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {
                    if (state.characterDetailInfo != null) {
                        if (state.isSaveChara != null) {
                            deleteClick()
                        } else {
                            insertClick()
                        }
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = HtmlCompat.fromHtml(
                    state.characterDetailInfo?.description ?: "",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            )
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.padding(start = 4.dp),
            onClick = { activity?.finish() }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}