package com.chs.youranimelist.presentation.browse.character

import android.app.Activity
import android.text.Html
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.presentation.ui.theme.Pink80
import com.chs.youranimelist.util.color

@Composable
fun CharacterDetailScreen(
    charaId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    val maxHeight = 420f
    val minHeight = 60f
    val d by with(LocalDensity.current) {
        remember { mutableStateOf(this.density) }
    }
    val toolbarHeightPx by with(LocalDensity.current) {
        remember { mutableStateOf(maxHeight.dp.roundToPx().toFloat()) }
    }
    val toolbarMinHeightPx by with(LocalDensity.current) {
        remember { mutableStateOf(minHeight.dp.roundToPx().toFloat()) }
    }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value =
                    newOffset.coerceIn(toolbarMinHeightPx - toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = toolbarOffsetHeightPx.value) {
        progress =
            ((toolbarHeightPx + toolbarOffsetHeightPx.value) / toolbarHeightPx - minHeight / maxHeight) / (1f - minHeight / maxHeight)
    }

    LaunchedEffect(viewModel, context) {
        viewModel.getCharacterDetail(charaId)
        viewModel.isSaveCharacter(charaId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        val characterInfo = state.characterDetailInfo?.character
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                top = maxHeight.dp,
                start = 8.dp,
                end = 8.dp
            )
        ) {
            items(characterInfo?.media?.edges?.size ?: 0) { idx ->
                ItemAnimeSmall(
                    item = characterInfo?.media?.edges?.get(idx)?.node?.animeList!!,
                    onClick = {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${characterInfo.media.edges[idx]?.node?.animeList!!.id}" +
                                    "/${characterInfo.media.edges[idx]?.node?.animeList!!.idMal ?: 0}"
                        )
                    }
                )
            }
        }

        CharacterBanner(
            height = ((toolbarHeightPx + toolbarOffsetHeightPx.value) / d).dp,
            progress = progress,
            state = state,
            insertClick = {
                viewModel.insertCharacter()
            }, deleteClick = {
                viewModel.deleteCharacter()
            }
        )
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Pink80)
        }
    }
}


@Composable
fun CharacterBanner(
    height: Dp,
    progress: Float,
    state: CharacterDetailState,
    insertClick: () -> Unit,
    deleteClick: () -> Unit
) {
    val characterInfo = state.characterDetailInfo?.character
    val activity = (LocalContext.current as? Activity)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .alpha(progress)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
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

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
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

//            Text(
//                text = "Description",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = HtmlCompat.fromHtml(
//                    state.characterDetailInfo?.character?.description ?: "",
//                    HtmlCompat.FROM_HTML_MODE_LEGACY
//                ).toString()
//            )
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colors.primarySurface),
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