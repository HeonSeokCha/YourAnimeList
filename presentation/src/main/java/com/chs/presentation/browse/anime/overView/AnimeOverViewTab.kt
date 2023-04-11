package com.chs.presentation.browse.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.chs.common.UiConst.GENRE_COLOR
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.color
import com.chs.presentation.common.ItemAnimeSmall

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeOverViewScreen(
    animeOverViewInfo: AnimeDetailInfo?,
    animeTheme: AnimeThemeInfo?,
    navController: NavController,
) {
    var expandedDescButton by remember { mutableStateOf(false) }
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

                Column(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (expandedDescButton) {
                        Text(
                            text = HtmlCompat.fromHtml(
                                animeOverViewInfo.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString()
                        )
                    } else {
                        Text(
                            text = HtmlCompat.fromHtml(
                                animeOverViewInfo.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString(),
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        onClick = { expandedDescButton = !expandedDescButton }
                    ) {
                        if (expandedDescButton) {
                            Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                        } else {
                            Icon(
                                imageVector = Icons.Filled.ArrowDownward,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            AnimeSummaryInfo(animeDetailInfo = animeOverViewInfo)

            if (!animeTheme?.openingThemes.isNullOrEmpty()) {
                Text(
                    text = "Opening Theme",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                animeTheme?.openingThemes?.forEach { themeTitle ->
                    Text(
                        text = themeTitle,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!animeTheme?.endingThemes.isNullOrEmpty()) {
                Text(
                    text = "Ending Theme",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                animeTheme?.endingThemes?.forEach { themeTitle ->
                    Text(
                        text = themeTitle,
                        fontSize = 13.sp
                    )
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

@Composable
private fun AnimeSummaryInfo(animeDetailInfo: AnimeDetailInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                top = 16.dp,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val summaryList = listOf(
            "Average" to "${animeDetailInfo.animeInfo.averageScore}%",
            "Mean" to "${animeDetailInfo.meanScore}%",
            "Popularity" to animeDetailInfo.popularScore.toString(),
            "Favorites" to animeDetailInfo.animeInfo.favourites.toString(),
        )
        summaryList.forEach {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = it.second,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = it.first,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}