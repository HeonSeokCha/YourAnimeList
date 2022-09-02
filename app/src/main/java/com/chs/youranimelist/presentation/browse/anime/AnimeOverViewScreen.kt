package com.chs.youranimelist.presentation.browse.anime

import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.util.Constant.GENRE_COLOR
import com.chs.youranimelist.util.color
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeOverViewScreen(
    animeId: Int,
    animeMalId: Int,
    navController: NavController,
    viewModel: AnimeOverViewViewModel = hiltViewModel(),
    scrollState: LazyListState,
    expandDesc: Boolean,
    changeExpand: (Boolean) -> Unit,
    calcScreenHeight: (Dp) -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val density = LocalDensity.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeOverView(animeId)
        viewModel.getAnimeTheme(animeMalId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .onGloballyPositioned { coordinates ->
                Log.e("AnimeOverViewScreen", with(density) { coordinates.size.height.toDp() }.toString())
            },
        state = scrollState,
        contentPadding = PaddingValues(
            vertical = 8.dp
        )
    ) {
        item {
            FlowRow(
                modifier = Modifier,
                mainAxisSpacing = 4.dp
            ) {
                state.animeOverViewInfo?.media?.genres?.forEach { genre ->
                    Chip(
                        onClick = {
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = GENRE_COLOR[genre]?.color ?: Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = genre.toString())
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

                if (expandDesc) {
                    Text(
                        text = HtmlCompat.fromHtml(
                            state.animeOverViewInfo?.media?.description ?: "",
                            Html.FROM_HTML_MODE_LEGACY
                        ).toString()
                    )


                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            changeExpand(!expandDesc)
                        }) {
                        Icon(imageVector = Icons.Filled.ArrowDropUp, contentDescription = null)
                    }
                } else {
                    Text(
                        text = HtmlCompat.fromHtml(
                            state.animeOverViewInfo?.media?.description ?: "",
                            Html.FROM_HTML_MODE_LEGACY
                        ).toString(),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            changeExpand(!expandDesc)
                        }) {
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                }
            }
        }


        items(state.animeOverThemeInfo?.openingThemes?.size ?: 0) { idx ->
            Text(text = state.animeOverThemeInfo?.openingThemes?.get(idx).toString())
        }

        items(state.animeOverThemeInfo?.endingThemes?.size ?: 0) { idx ->
            Text(text = state.animeOverThemeInfo?.endingThemes?.get(idx).toString())
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(state.animeOverViewInfo?.media?.relations?.relationsEdges?.size ?: 0) { idx ->
                    ItemAnimeSmall(
                        item = state.animeOverViewInfo?.media?.relations?.relationsEdges?.get(idx)?.relationsNode?.animeList!!
                    ) {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${
                                        state.animeOverViewInfo.media.relations.relationsEdges[idx]?.relationsNode?.animeList!!.id
                                    }" +
                                    "/${
                                        state.animeOverViewInfo.media.relations.relationsEdges[idx]?.relationsNode?.animeList!!.idMal ?: 0
                                    }"
                        )
                    }
                }
            }
        }
    }
}