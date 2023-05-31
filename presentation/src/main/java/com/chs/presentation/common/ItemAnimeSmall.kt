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
import com.chs.presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.isNotEmptyValue
import com.chs.presentation.ui.theme.YourAnimeListTheme
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun ItemAnimeSmall(
    item: AnimeInfo?,
    onClick: () -> Unit
) {
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
                    .clip(RoundedCornerShape(5.dp))
                    .placeholder(item?.imageUrl == null),
                model = item?.imageUrl,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .placeholder(item == null),
                text = item?.title ?: "title Preview",
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .placeholder(item == null),
                    text = UiConst.mediaStatus[item?.status]?.first ?: "",
                    color = Color(UiConst.mediaStatus[item?.status]?.second ?: 0xFF888888),
                    fontSize = 12.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (item?.seasonYear.isNotEmptyValue) {
                        Text(
                            modifier = Modifier
                                .placeholder(item == null),
                            textAlign = TextAlign.Left,
                            text = "${item?.seasonYear ?: 2000}",
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    if (item?.averageScore.isNotEmptyValue) {
                        Text(
                            modifier = Modifier
                                .placeholder(item == null),
                            textAlign = TextAlign.Right,
                            text = buildAnnotatedString {
                                appendInlineContent(
                                    UiConst.AVERAGE_SCORE_ID,
                                    UiConst.AVERAGE_SCORE_ID
                                )
                                append("${item?.averageScore ?: 99}")
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

@Preview
@Composable
fun PreviewItemAnimeSmall() {
    YourAnimeListTheme {
        val animeInfo = AnimeInfo(
            id = 0,
            idMal = 0,
            title = "Testasdkljhasodihaiosdjhaoisdjioasdasdasdasd",
            imageUrl = null,
            imagePlaceColor = null,
            averageScore = 1000,
            favourites = 1234,
            seasonYear = 2023,
            season = "",
            format = "TV",
            status = "RELEASING"
        )
        ItemAnimeSmall(item = animeInfo) { }
    }
}