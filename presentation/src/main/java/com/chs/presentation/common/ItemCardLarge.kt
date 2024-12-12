package com.chs.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.chs.presentation.isNotEmptyValue
import com.chs.presentation.ui.theme.Pink80

@Composable
fun ItemCardLarge(
    imageUrl: String?,
    title: String?,
    subTitle: String?,
    scoreTitle: List<Int?>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Pink80
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(190.dp)
                    .align(Alignment.CenterStart)
                    .placeholder(visible = imageUrl == null),
                model = imageUrl,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 158.dp,
                        top = 8.dp
                    )
                    .align(Alignment.TopStart)
            ) {
                Text(
                    modifier = Modifier
                        .placeholder(visible = title == null),
                    text = title ?: UiConst.TITLE_PREVIEW,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .placeholder(visible = subTitle == null),
                    text = subTitle ?: UiConst.UNKNOWN,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 158.dp,
                        bottom = 16.dp
                    )
            ) {
                if (scoreTitle.first().isNotEmptyValue) {
                    Text(
                        text = buildAnnotatedString {
                            if (scoreTitle.size > 1) {
                                appendInlineContent(
                                    UiConst.AVERAGE_SCORE_ID,
                                    UiConst.AVERAGE_SCORE_ID
                                )
                            } else {
                                appendInlineContent(
                                    UiConst.FAVOURITE_ID,
                                    UiConst.FAVOURITE_ID
                                )
                            }
                            append("${scoreTitle[0]}")
                        },
                        inlineContent = UiConst.inlineContent,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (scoreTitle.size > 1) {
                    if (scoreTitle.last().isNotEmptyValue) {
                        Text(
                            text = buildAnnotatedString {
                                appendInlineContent(
                                    UiConst.FAVOURITE_ID,
                                    UiConst.FAVOURITE_ID
                                )
                                append("${scoreTitle[1]}")
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
}