package com.chs.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.presentation.main.Screen
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.color
import com.chs.common.UiConst
import com.chs.common.UiConst.GENRE_COLOR

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeOverViewScreen(
    animeOverViewInfo: AnimeDetailInfo?,
    animeTheme: AnimeThemeInfo?,
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
        if (animeOverViewInfo != null) {
            if (animeOverViewInfo.genres.isNotEmpty()) {
                FlowRow {
                    animeOverViewInfo.genres.forEach { genre ->
                        AssistChip(
                            modifier = Modifier
                                .padding(end = 4.dp),
                            onClick = {
//                                navController.navigate("${Screen.SortListScreen.route}/$genre")
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

            if (animeOverViewInfo.description.isNotEmpty()) {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

                Text(
                    text = HtmlCompat.fromHtml(
                        animeOverViewInfo.description,
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

            if (animeOverViewInfo.animeRelationInfo.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(animeOverViewInfo.animeRelationInfo) { animeInfo ->
                        ItemAnimeSmall(
                            item = animeInfo.animeBasicInfo
                        ) {
                            navController.navigate(
                                "${BrowseScreen.AnimeDetailScreen.route}/" +
                                        "${animeInfo.animeBasicInfo.id}" +
                                        "/${animeInfo.animeBasicInfo.idMal}"
                            )
                        }
                    }
                }
            }
        }
    }
}