package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
        animeTheme?.openingThemes?.forEach { themeTitle ->
            Text(text = themeTitle)
        }

        animeTheme?.endingThemes?.forEach { themeTitle ->
            Text(text = themeTitle)
        }

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