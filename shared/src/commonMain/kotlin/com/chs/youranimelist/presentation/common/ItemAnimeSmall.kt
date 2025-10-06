package com.chs.youranimelist.presentation.common

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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.isNotEmptyValue
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ItemAnimeSmall(
    item: AnimeInfo? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp)
            .clickable { onClick() },
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
                ShimmerImage(
                    modifier = Modifier
                        .width(130.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmer(visible = item == null),
                    url = item?.imageUrl,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier
                        .shimmer(visible = item == null),
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
                        .shimmer(visible = item == null),
                    text = UiConst.mediaStatus[item?.status]?.first ?: "FINISHED",
                    color = Color(UiConst.mediaStatus[item?.status]?.second ?: 0xFF888888),
                    fontSize = 12.sp
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (item != null) {
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

                        if (item.averageScore.isNotEmptyValue) {
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