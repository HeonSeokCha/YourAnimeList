package com.chs.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.chs.domain.model.AnimHomeBannerInfo
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.UiConst.GENRE_COLOR
import com.chs.presentation.color
import com.chs.presentation.ui.theme.YourAnimeListTheme
import com.google.accompanist.placeholder.material.placeholder

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemHomeBanner(
    banner: AnimHomeBannerInfo?,
    onClick: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .clickable {
                if (banner != null) {
                    onClick(
                        banner.animeInfo.id,
                        banner.animeInfo.idMal
                    )
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp, 220.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp, 220.dp)
                    .background(Color.LightGray),
                model = banner?.animeInfo?.imageUrl,
                placeholder = ColorPainter(
                    banner?.animeInfo?.imagePlaceColor?.color ?: Color.LightGray
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = banner?.animeInfo?.title ?: UiConst.TITLE_PREVIEW,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = banner?.studioTitle ?: UiConst.TITLE_PREVIEW,
                    color = banner?.animeInfo?.imagePlaceColor?.color ?: Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Column(
            modifier = Modifier.height(220.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = "${banner?.episode} Episodes aired on",
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = banner?.startDate ?: "",
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = HtmlCompat.fromHtml(
                    banner?.description ?: UiConst.TITLE_PREVIEW,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString(),
                fontSize = 12.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            if (banner?.genres != null) {
                FlowRow(
                    modifier = Modifier
                        .background("#eff7fb".color)
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(start = 2.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    banner.genres.forEach { genre ->
                        SuggestionChip(
                            modifier = Modifier
                                .height(24.dp)
                                .padding(horizontal = 2.dp),
                            onClick = { },
                            label = {
                                Text(
                                    text = genre ?: "Unknown",
                                    fontSize = 12.sp
                                )
                            }, colors = AssistChipDefaults.assistChipColors(
                                containerColor = GENRE_COLOR[genre]?.color ?: Color.Black,
                                labelColor = Color.White
                            ), border = AssistChipDefaults.assistChipBorder(
                                enabled = true,
                                borderColor = GENRE_COLOR[genre]?.color ?: Color.Black
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewItemHomeBanner() {
    YourAnimeListTheme {
        val animeInfo = AnimeInfo(
            id = 0,
            idMal = 0,
            title = "Kimetsu no Yaiba: Yuukaku-henKimetsu no Yaiba: Yuukaku-henKimetsu no Yaiba: Yuukaku-henKimetsu no Yaiba: Yuukaku-hen",
            imageUrl = null,
            imagePlaceColor = "#dc866c",
            averageScore = 0,
            favourites = 80,
            seasonYear = 0,
            season = "",
            format = "TV",
            status = "RELEASING"
        )
        ItemHomeBanner(
            AnimHomeBannerInfo(
                animeInfo = animeInfo,
                studioTitle = "TESt",
                episode = "12",
                description = "The adventure is over but life goes on for an elf mage just beginning to learn what living is all about" +
                        "oes on for an elf mage just beginning to learn what living is all aboutoes on for an elf mage just beginning to learn what living is all about",
                startDate = "2020/4/6",
                genres = List<String>(3) { "1234" }
            )
        ) { id, idMal ->

        }
    }
}
