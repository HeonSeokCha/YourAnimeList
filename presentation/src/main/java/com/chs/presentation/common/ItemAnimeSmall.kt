package com.chs.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.common.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemAnimeSmall(
    item: AnimeInfo,
    onClick: () -> Unit
) {
    val starId = "starId"
    val inlineContent = mapOf(
        Pair(
            starId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                )
            })
    )
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 4.dp,
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(5.dp)),
                model = item.imageUrl,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                lineHeight = 14.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 4.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = UiConst.mediaStatus[item.status]?.first ?: "",
                        color = Color(UiConst.mediaStatus[item.status]?.second ?: 0xFF888888),
                        fontSize = 12.sp
                    )

                    Text(
                        text = if (item.seasonYear == 0) "" else item.seasonYear.toString(),
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )
                }

                if (item.averageScore != 0) {
                    Text(
                        text = buildAnnotatedString {
                            appendInlineContent(starId, starId)
                            append(item.averageScore.toString())
                        },
                        inlineContent = inlineContent,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, false),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemAnimeSmall() {
    YourAnimeListTheme {
        val animeInfo = AnimeInfo(
            id = 0,
            idMal = 0,
            title = "Test",
            imageUrl = null,
            imagePlaceColor = null,
            averageScore = 87,
            favourites = 1234,
            seasonYear = 2019,
            season = "",
            format = "TV",
            status = "FINISHED"
        )
        ItemAnimeSmall(item = animeInfo) { }
    }
}