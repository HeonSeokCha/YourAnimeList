package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.domain.model.AnimeDetails
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.home.ItemAnimeSmall
import com.chs.youranimelist.util.Constant.GENRE_COLOR
import com.chs.youranimelist.util.color

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun AnimeOverViewScreen(
    animeOverViewInfo: AnimeDetailQuery.Data?,
    animeTheme: AnimeDetails?,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 16.dp
            )
    ) {
        if (!animeOverViewInfo?.media?.genres.isNullOrEmpty()) {
            FlowRow {
                animeOverViewInfo?.media?.genres?.forEach { genre ->
                    Chip(
                        modifier = Modifier
                            .padding(end = 4.dp),
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

        if (animeOverViewInfo?.media?.description != null) {
            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

            Text(
                text = HtmlCompat.fromHtml(
                    animeOverViewInfo.media.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            )
        }

        if (!animeTheme?.openingThemes.isNullOrEmpty()) {
            animeTheme?.openingThemes?.forEach { themeTitle ->
                Text(text = themeTitle)
            }
        }


        if (!animeTheme?.endingThemes.isNullOrEmpty()) {
            animeTheme?.endingThemes?.forEach { themeTitle ->
                Text(text = themeTitle)
            }
        }

        Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        if (animeOverViewInfo?.media?.relations?.relationsEdges != null) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(animeOverViewInfo.media.relations.relationsEdges) { edge ->
                    ItemAnimeSmall(
                        item = edge?.relationsNode?.animeList!!
                    ) {
                        navController.navigate(
                            "${BrowseScreen.AnimeDetailScreen.route}/" +
                                    "${edge.relationsNode.animeList.id}" +
                                    "/${edge.relationsNode.animeList.idMal}"
                        )
                    }
                }
            }
        }
    }
}