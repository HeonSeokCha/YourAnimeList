package com.chs.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.chs.presentation.main.Screen
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.color

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeOverViewScreen(
    animeOverViewInfo: AnimeDetailQuery.Data?,
    animeTheme: com.chs.domain.model.AnimeThemeInfo?,
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
                    AssistChip(
                        modifier = Modifier
                            .padding(end = 4.dp),
                        onClick = {
                            navController.navigate("${Screen.SortListScreen.route}/$genre")
                        }, label = {
                            Text(text = genre.toString())
                        }, colors = AssistChipDefaults.assistChipColors(
                            containerColor = GENRE_COLOR[genre]?.color ?: Color.Black,
                            labelColor = Color.White
                        ), border = AssistChipDefaults.assistChipBorder(
                            borderColor = GENRE_COLOR[genre]?.color ?: Color.Black
                        )
                    )
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