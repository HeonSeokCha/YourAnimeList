package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.presentation.Screen
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.util.Constant.GENRE_COLOR
import com.chs.youranimelist.util.color
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeOverViewScreen(
    animeOverViewInfo: AnimeDetailQuery.Data?,
    animeTheme: AnimeDetails?,
    navController: NavController,
    scrollState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            ),
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
                animeOverViewInfo?.media?.genres?.forEach { genre ->
                    Chip(
                        onClick = {
                            navController.navigate("${Screen.SortListScreen.route}/$genre")
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

                Text(
                    text = HtmlCompat.fromHtml(
                        animeOverViewInfo?.media?.description ?: "",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                )
            }
        }


        items(animeTheme?.openingThemes?.size ?: 0) { idx ->
            Text(text = animeTheme?.openingThemes?.get(idx).toString())
        }

        items(animeTheme?.endingThemes?.size ?: 0) { idx ->
            Text(text = animeTheme?.endingThemes?.get(idx).toString())
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(animeOverViewInfo?.media?.relations?.relationsEdges?.size ?: 0) { idx ->
                    ItemAnimeSmall(
                        item = animeOverViewInfo?.media?.relations?.relationsEdges?.get(idx)?.relationsNode?.animeList!!
                    ) {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${animeOverViewInfo.media.relations.relationsEdges[idx]?.relationsNode?.animeList!!.id}" +
                                    "/${animeOverViewInfo.media.relations.relationsEdges[idx]?.relationsNode?.animeList!!.idMal ?: 0}"
                        )
                    }
                }
            }
        }
    }
}