package com.chs.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.isNotEmptyValue
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun ItemAnimeLarge(
    anime: AnimeInfo?,
    clickAble: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clickable {
                clickAble()
            },
        colors = CardDefaults.cardColors(
            containerColor = Pink80
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(190.dp)
                    .placeholder(anime == null),
                model = anime?.imageUrl,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
            ) {
                Text(
                    modifier = Modifier
                        .placeholder(anime == null),
                    text = anime?.title ?: UiConst.TITLE_PREVIEW,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .placeholder(anime == null),
                    text = if (anime?.seasonYear.isNotEmptyValue) {
                        "${anime?.seasonYear ?: 2000} ⦁ " +
                                (UiConst.mediaStatus[anime?.status]?.first ?: UiConst.UNKNOWN)
                    } else {
                        UiConst.mediaStatus[anime?.status]?.first ?: UiConst.UNKNOWN
                    },
                    color = Color.White
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    if (anime?.averageScore.isNotEmptyValue) {
                        Text(
                            modifier = Modifier
                                .placeholder(anime == null),
                            text = buildAnnotatedString {
                                appendInlineContent(
                                    UiConst.AVERAGE_SCORE_ID,
                                    UiConst.AVERAGE_SCORE_ID
                                )
                                append("${anime?.averageScore ?: 0}")
                            },
                            inlineContent = UiConst.inlineContent,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        modifier = Modifier
                            .placeholder(anime == null),
                        text = buildAnnotatedString {
                            appendInlineContent(
                                UiConst.FAVOURITE_ID,
                                UiConst.FAVOURITE_ID
                            )
                            append("${anime?.favourites ?: 0}")
                        },
                        inlineContent = UiConst.inlineContent,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}
