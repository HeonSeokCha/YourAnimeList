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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.AnimHomeBannerInfo
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.UiConst.GENRE_COLOR
import com.chs.presentation.color
import com.chs.presentation.common.PlaceholderHighlight
import com.chs.presentation.common.placeholder
import com.chs.presentation.common.shimmer
import com.chs.presentation.ui.theme.YourAnimeListTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemHomeBanner(
    banner: AnimHomeBannerInfo?,
    onClick: (Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.White)
            .clickable {
                if (banner != null) {
                    onClick(
                        banner.animeInfo.id,
                        banner.animeInfo.idMal
                    )
                }
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(220.dp)
                .background(Color.LightGray)
                .placeholder(visible = banner == null),
            model = banner?.animeInfo?.imageUrl,
            placeholder = ColorPainter(
                banner?.animeInfo?.imagePlaceColor?.color ?: Color.LightGray
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .placeholder(visible = banner == null),
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
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                    )
                    .align(Alignment.BottomStart)
                    .placeholder(visible = banner == null),
                text = banner?.studioTitle ?: UiConst.TITLE_PREVIEW,
                color = banner?.animeInfo?.imagePlaceColor?.color ?: Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                maxLines = 1, overflow = TextOverflow.Ellipsis ) }


        if (banner?.genres != null) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
                    .padding(start = 2.dp)
                    .align(Alignment.BottomEnd),
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

        if (banner?.episode != 0) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp,
                        bottom = 4.dp
                    )
                    .align(Alignment.TopEnd)
                    .placeholder(visible = banner == null),
                text = "${banner?.episode} Episodes aired on",
                fontSize = 12.sp
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(
                    start = 4.dp,
                    end = 4.dp,
                    top = 32.dp,
                    bottom = 4.dp
                )
                .align(Alignment.TopEnd)
                .placeholder(visible = banner == null),
            text = banner?.startDate ?: "",
            fontSize = 12.sp
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(
                    start = 4.dp,
                    end = 4.dp,
                    top = 64.dp,
                    bottom = 4.dp
                )
                .align(Alignment.TopEnd)
                .placeholder(visible = banner == null),
            text = AnnotatedString.fromHtml(
                htmlString = banner?.description ?: UiConst.TITLE_PREVIEW
            ),
            fontSize = 12.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 14.sp
        )
    }
}


@Preview
@Composable
fun PreviewItemHomeBanner() {
    YourAnimeListTheme {
        val animeInfo = AnimeInfo(
            id = 0,
            idMal = 0,
            title = "HunterXHunter(2011)",
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
                episode = 12,
                description = "The adventure is over but life goes on for an elf mage just beginning to learn what living is all about" +
                        "oes on for an elf mage just beginning to learn what living is all aboutoes on for an elf mage just beginning to learn what living is all about",
                startDate = "2020/04/6",
                genres = List<String>(3) { "1234" }
            )
        ) { id, idMal ->

        }
    }
}
