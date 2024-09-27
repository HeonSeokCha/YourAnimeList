package com.chs.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemAnimeSmall(
    item: AnimeInfo?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 4.dp,
                    start = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(AbsoluteAlignment.TopLeft),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(130.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .placeholder(
                            visible = item == null,
                            highlight = PlaceholderHighlight.shimmer(),
                        ),
                    model = item?.imageUrl, placeholder = ColorPainter(Color.LightGray),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = item == null,
                            highlight = PlaceholderHighlight.shimmer(),
                        ),
                    text = item?.title ?: "title Preview",
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(AbsoluteAlignment.BottomLeft),
            ) {
                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = item == null,
                            highlight = PlaceholderHighlight.shimmer(),
                        ),
                    text = UiConst.mediaStatus[item?.status]?.first ?: "FINISHED",
                    color = Color(UiConst.mediaStatus[item?.status]?.second ?: 0xFF888888),
                    fontSize = 12.sp
                )


                if (item != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            textAlign = TextAlign.Left,
                            text = if (item.seasonYear == 0) {
                                ""
                            } else {
                                "${item.seasonYear}"
                            },
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        if (item.averageScore != 0) {
                            Text(
                                textAlign = TextAlign.Right,
                                text = buildAnnotatedString {
                                    appendInlineContent(
                                        UiConst.AVERAGE_SCORE_ID,
                                        UiConst.AVERAGE_SCORE_ID
                                    )
                                    append("${item.averageScore}")
                                },
                                inlineContent = UiConst.inlineContent,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }
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
            title = "",
            imageUrl = null,
            imagePlaceColor = null,
            averageScore = 0,
            favourites = 80,
            seasonYear = 0,
            season = "",
            format = "TV",
            status = "RELEASING"
        )
        ItemAnimeSmall(item = animeInfo) { }
    }
}