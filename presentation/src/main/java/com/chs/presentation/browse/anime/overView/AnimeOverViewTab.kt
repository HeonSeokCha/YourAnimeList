package com.chs.presentation.browse.anime.overView

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.chs.common.UiConst.GENRE_COLOR
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.AnimeRelationInfo
import com.chs.domain.model.AnimeThemeInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.color
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.main.Screen


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
                AnimeGenreChips(
                    navController,
                    animeOverViewInfo.genres
                )
            }

            if (animeOverViewInfo.description.isNotEmpty()) {
                AnimeDescription(
                    description = animeOverViewInfo.description,
                    expandedDescButton = expandedDescButton
                ) {
                    expandedDescButton = !expandedDescButton
                }
            }

            AnimeScoreInfo(animeDetailInfo = animeOverViewInfo)

            AnimeSummaryInfo(animeOverViewInfo)

            if (animeTheme?.openingThemes != null && animeTheme.openingThemes.isNotEmpty()) {
                AnimeThemeInfo(
                    title = "Opening Theme",
                    songList = animeTheme.openingThemes
                )
            }

            if (animeTheme?.endingThemes != null && animeTheme.endingThemes.isNotEmpty()) {
                AnimeThemeInfo(
                    title = "Ending Theme",
                    songList = animeTheme.endingThemes
                )
            }

            if (animeOverViewInfo.animeRelationInfo.isNotEmpty()) {
                AnimeRelationInfo(animeList = animeOverViewInfo.animeRelationInfo) {
                    navController.navigate(
                        "${BrowseScreen.AnimeDetailScreen.route}/" +
                                "${it.id}" +
                                "/${it.idMal}"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimeGenreChips(
    navController: NavController,
    list: List<String?>
) {
    FlowRow {
        list.forEach { genre ->
            if (genre != null) {
                AssistChip(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    onClick = {
                        navController.navigate("${Screen.SortListScreen.route}/$genre")
                    }, label = {
                        Text(text = genre)
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
}

@Composable
private fun AnimeThemeInfo(
    title: String,
    songList: List<String>
) {
    Text(
        text = title,
        fontSize = 14.sp,
        color = Color.Gray,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    songList.forEach { themeTitle ->
        Text(
            text = themeTitle,
            fontSize = 13.sp
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun AnimeRelationInfo(
    animeList: List<AnimeRelationInfo>,
    onClick: (animeInfo: AnimeInfo) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(animeList) { animeInfo ->
            ItemAnimeSmall(
                item = animeInfo.animeBasicInfo
            ) {
                onClick(animeInfo.animeBasicInfo)
            }
        }
    }
}

@Composable
private fun AnimeSummaryInfo(
    animeDetailInfo: AnimeDetailInfo
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp)
    ) {
        AnimeSummaryInfoSmall("Romaji", animeDetailInfo.animeInfo.title)

        if (animeDetailInfo.titleEnglish.isNotEmpty()) {
            AnimeSummaryInfoSmall("English", animeDetailInfo.titleEnglish)
        }
        if (animeDetailInfo.titleNative.isNotEmpty()) {
            AnimeSummaryInfoSmall("Native", animeDetailInfo.titleNative)
        }

        if (animeDetailInfo.animeInfo.format.isNotEmpty()) {
            AnimeSummaryInfoSmall("Format", animeDetailInfo.animeInfo.format)
        }

        if (animeDetailInfo.episode != 0) {
            AnimeSummaryInfoSmall("Episode", "${animeDetailInfo.episode}Ep")
        }

        if (animeDetailInfo.duration != 0) {
            AnimeSummaryInfoSmall("Durations", "${animeDetailInfo.duration}Min")
        }

        if (animeDetailInfo.startDate.isNotEmpty()) {
            AnimeSummaryInfoSmall("StartDate", animeDetailInfo.startDate)
        }

        if (animeDetailInfo.endDate.isNotEmpty()) {
            AnimeSummaryInfoSmall("EndDate", animeDetailInfo.endDate)
        }

        if (animeDetailInfo.animeInfo.seasonYear != 0) {
            AnimeSummaryInfoSmall(
                "Season",
                "${animeDetailInfo.animeInfo.seasonYear} ${animeDetailInfo.animeInfo.season}"
            )
        }

        if (animeDetailInfo.studioInfo.firstOrNull() != null) {
            AnimeSummaryInfoSmall(
                "Studio",
                animeDetailInfo.studioInfo.firstOrNull { it.isMainStudio }?.name ?: ""
            )
        }
    }
}

@Composable
private fun AnimeSummaryInfoSmall(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$title : ",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
    }

}

@Composable
private fun AnimeScoreInfo(animeDetailInfo: AnimeDetailInfo) {
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

@Composable
private fun AnimeDescription(
    description: String,
    expandedDescButton: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val spannedText = HtmlCompat.fromHtml(
            description,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        if (expandedDescButton) {
            AndroidView(
                modifier = Modifier,
                factory = { TextView(it) },
                update = {
                    it.text = spannedText
                    it.setTextColor(android.graphics.Color.parseColor("#000000"))
                    it.textSize = 16f
                }
            )
        } else {
            AndroidView(
                modifier = Modifier,
                factory = { TextView(it) },
                update = {
                    it.text = spannedText
                    it.maxLines = 5
                    it.ellipsize = TextUtils.TruncateAt.END
                    it.setTextColor(android.graphics.Color.parseColor("#000000"))
                    it.textSize = 16f
                }
            )
        }

        if (description.length > 400) {
            if (!expandedDescButton) {
                Button(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                    onClick = { onClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDownward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
