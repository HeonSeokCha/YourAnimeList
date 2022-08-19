package com.chs.youranimelist.presentation.browse.anime

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chs.youranimelist.presentation.Screen
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.util.Constant
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
) {
    val state = viewModel.state
    val context = LocalContext.current

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
            ),
        state = scrollState
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
                    AndroidView(
                        factory = { context -> TextView(context) },
                        update = {
                            it.text = HtmlCompat.fromHtml(
                                state.animeOverViewInfo?.media?.description ?: "",
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                        }
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
                    AndroidView(
                        factory = { context -> TextView(context) },
                        update = {
                            it.text = HtmlCompat.fromHtml(
                                state.animeOverViewInfo?.media?.description ?: "",
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                            it.maxLines = 5
                            it.ellipsize = TextUtils.TruncateAt.END
                        }
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
                    .wrapContentHeight()
                    .padding(
                        start = 8.dp,
                        end = 8.dp
                    ),
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
                                        state.animeOverViewInfo.media.relations.relationsEdges[idx]?.relationsNode?.animeList!!.idMal
                                    }"
                        )
                    }
                }
            }
        }
    }
}